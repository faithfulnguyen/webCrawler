/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layerfour;


import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Trung_Tin
 */
public class MongoDB {
    private Mongo mongo;
    private DBCollection collection;
    private DB database;
    public MongoDB(){
         this.mongo = null;
    }
    public MongoDB(String mg, String cl) throws IOException{
        this.mongo = new Mongo("localhost", 27017);
    }
    public void insertData(String dbName, String colName, String title, ArrayList<ArrayList<ArrayList<String>>> comment) throws IOException {
	DB db = this.mongo.getDB(dbName);
	// get a single collection
	DBCollection collection = db.getCollection(colName);
        this.collection = collection;
        
        BasicDBObject document = new BasicDBObject();
        String parent = "comment";
        document.put("title", title);
        BasicDBObject cmts = new BasicDBObject();
        int idx = 0;
        for( int i = 0; i < comment.size(); i++){
             for(int j = 0; j < comment.get(i).size(); j++){
                cmts.put( "comment_" + idx++, insertCommment(comment.get(i).get(j)));
            }
        }
        document.put(parent, cmts);
        collection.insert(document);
    }
    public  BasicDBObject insertCommment(ArrayList<String> cmt){
        BasicDBObject document = new BasicDBObject();
        document.put("name", cmt.get(0));
        document.put("region",cmt.get(1));
        document.put("purchased_at", cmt.get(2));
        document.put("purchased", cmt.get(3));
        document.put("title", cmt.get(4));
        document.put("content", cmt.get(5));
        document.put("thank_count", cmt.get(6));
        document.put("rating", cmt.get(7));
        document.put("thanked", cmt.get(8));
        document.put("status", cmt.get(9));
        if(cmt.size() > 10){
            int idx = 0;
            BasicDBObject allReply = new BasicDBObject();
            for( int i = 0; i < (cmt.size() - 12); i += 3){
                BasicDBObject rep = new BasicDBObject();
                rep.put("fullname", cmt.get(10 + i));
                rep.put("content", cmt.get(11 + i));
                rep.put("create_at", cmt.get(12 + i));
                allReply.put("reply_comment" + idx++, rep);
            }
        document.put("replyComment", allReply);
        }
        return document;
    }
}
