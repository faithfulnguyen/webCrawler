/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layerfour;

/**
 *
 * @author Trung_Tin
 */
import com.mongodb.Mongo;
import java.io.IOException;
import static java.lang.System.exit;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;



/**
 *
 * @author Trung_Tin
 */
public class Crawler {
    private int numberCmts;
    private String id;
    private String tailUrl;
    private String prefixUrl;
    private String subfixUrl;
    private String title;
    private ArrayList<ArrayList<ArrayList<String>>> allComment;
    
    public Crawler(){
        this.numberCmts = -1;
        this.id = null;
        this.tailUrl = null;
        this.prefixUrl = null;
        this.subfixUrl = null;
        this.title = null;
        this.allComment = null;
    }
    
    public Crawler(int nbCmts, String id, String tail, String tmprefix, String tmpSubfix, String tit, ArrayList al){
        this.numberCmts = nbCmts;
        this.id = id;
        this.tailUrl = tail;
        this.prefixUrl = tmprefix;
        this.subfixUrl = tmpSubfix;
        this.title = tit;
        this.allComment = al;
    }
    
    public int getComment(String url, String collection)throws IOException, JSONException{
        MongoDB mg = new MongoDB("tikicomment", collection);
        
        //System.out.println("url" + url);
        
        int iss = numberComments(url);
        
        getLinkAjaxComment();
        
        getIdComment(url);
        
        //System.out.println("isss: " + iss);
        
        if(iss == -1)
            return -1;
       
        this.allComment = getCommentAllPage();
       
       //System.out.println(this.allComment);
        mg.insertData("tikicomment", collection, this.title, this.allComment);
        return 1;
    }
      
    public ArrayList<ArrayList<ArrayList<String>>> getCommentAllPage()throws IOException, JSONException{
        ArrayList<ArrayList<ArrayList<String>>> al = new ArrayList();
        try{ 
            for (int i = 1; i <= this.numberCmts; i++) {
                try{
                    String url = this.prefixUrl + this.id + this.subfixUrl + i + this.tailUrl;
                    String json = (String)Jsoup.connect(url).ignoreContentType(true).execute().body();
                    ArrayList<ArrayList<String>> tmp = new ArrayList();
                    
                    tmp = getInforComments(json);
                    if(tmp.isEmpty())
                        continue;
                    al.add(tmp);
                    
                }catch(UnknownHostException e)
                {
                    System.out.println("Unknown Host Exception");
                }
                catch (NullPointerException e){
                    System.out.println("Null Pointer Exception");
                }
                catch (HttpStatusException e){
                    System.out.println("Http Status Exception");
                }
                catch (SocketTimeoutException e){
                    System.out.println("Socket Timeout Exception");
                }
            }
        }
        catch(java.net.MalformedURLException e){
            e.printStackTrace();
            System.out.println("Exception");
        }
        return al;
    }
    
   
    public void getIdComment(String url){
        String[] tmp = url.split(".html");
        String [] id = tmp[0].split("-");
        id[id.length - 1] = id[id.length - 1].substring(1);
        this.id = id[id.length - 1];
    }
    
    public void getLinkAjaxComment(){
        this.prefixUrl = "http://tiki.vn/ajax/reviews?product_id=" ;
        this.subfixUrl = "&sort=thank_count|desc&include=comments&page=";
        this.tailUrl = "&limit=5";
    }   
    
    public String classificationComent(int rate){
        if(rate == 1)
            return "không đồng tình";
        if(rate == 2)
            return "trung tính";
        return "đồng tình";  
    }

    public String booleanToString(boolean obj){
         return obj ? "true" : "false";
    }
    public ArrayList<ArrayList<String>> getInforComments(String json) throws IOException, JSONException{
       JSONObject obj;
        try{
            obj = new JSONObject(json);
        }catch(JSONException js){
            return null;
        }
        JSONArray geodata = obj.getJSONArray("data");
        ArrayList<ArrayList<String>> all = new ArrayList();
        
        final int n = geodata.length();
        for (int i = 0; i < n; ++i) {
            ArrayList<String> cmt = new ArrayList();
            final JSONObject person = geodata.getJSONObject(i);
            
            try{
                cmt.add(person.getJSONObject("created_by").getString("name"));
            }catch(JSONException js){
                continue;
            }
            try{
                cmt.add(person.getJSONObject("created_by").get("region").toString());
            }catch(JSONException js){
                cmt.add("null");
            }
           
            try{
                cmt.add(person.getJSONObject("created_by").get("purchased_at").toString());
            }catch(JSONException js){
                cmt.add("null");
            }
            try{  
                cmt.add(booleanToString(person.getJSONObject("created_by").getBoolean("purchased")));
            }catch(JSONException js){
                cmt.add("null");
            }
           
            try{
                cmt.add(person.getString("title"));
            }catch(JSONException js){
                cmt.add("err");
            }
            try{
                cmt.add(person.getString("content"));
            }catch(JSONException js){
                cmt.add("error");
            }
            try{
            cmt.add(String.valueOf(person.getInt("thank_count")));
            }catch(JSONException js){
                cmt.add(null);
            }
            try{
                cmt.add(String.valueOf(person.getInt("rating")));
            }catch(JSONException js){
                cmt.add(null);
            }
            try{
                cmt.add(booleanToString(person.getBoolean("thanked")));
            }catch(JSONException js){
                cmt.add(null);
            }
            //status
            try{
                cmt.add(classificationComent((person.getInt("rating"))));
            }catch(JSONException js){
                cmt.add(null);
            }
           
            JSONArray jr1 = person.getJSONArray("comments");
            for(int j = 0;j < jr1.length(); j++)
            {
               JSONObject jb2 = jr1.getJSONObject(j);
               try{
               cmt.add(jb2.get("fullname").toString());
               }catch(JSONException js){
                   cmt.add(null);
               }
               try{
                    cmt.add(jb2.get("content").toString());
               }catch(JSONException js){
                   cmt.add(null);
               }
               try{
               cmt.add(jb2.get("create_at").toString());
               }catch(JSONException js){
                   cmt.add(null);
               }
            }
            all.add(cmt);
        }
        return all;
    }
    
    public int numberComments(String url) throws IOException{
        try{
            Document doc = Jsoup.connect(url).method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                    .timeout(100*1000)
                    .ignoreHttpErrors(true)
                    .get();
            Elements el = doc.select("div[class=\"item-box\"]");
            Element nm = el.select("h1.item-name").first();
            this.title = nm.text();
            Elements ele = doc.select("div[class=\"item-rating\"]");
            Element link = ele.select("a[href]").first();
            
            String nb = link.text().replace('(', ' ');
            String []id = nb.split(" ");
            if(id[1].length() >= 1){
                int number = Integer.parseInt(id[1]);
                float md = number % 5;
                if( md != 0){
                    this.numberCmts = number / 5 + 1;
                }
                else this.numberCmts = number / 5;
                return 1;
            }else
                this.numberCmts = 0;
        }catch(UnknownHostException e){
            System.out.println("Unknown Host Exception");
        }
        catch (NullPointerException e){
            System.out.println("Null Pointer Exception");
        }
        catch (HttpStatusException e){
            System.out.println("Http Status Exception");
        }catch (SocketTimeoutException e){
            System.out.println("Socket Timeout Exception");
        }catch(SocketException s){
            System.out.println("Socket Exception");
        }
        return -1;
    }
}


