/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webtikicrawler;

import com.mongodb.DBCursor;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import webtikicrawler.LayerTwo;
import webtikicrawler.LayerThree;
import layerfour.Book;
import layerfour.MongoDB;
import org.json.JSONException;
import org.jsoup.Connection.Method;
import org.jsoup.HttpStatusException;
import org.jsoup.nodes.Element;

/**
 *
 * @author Trung_Tin
 */
public class LayerOne {
    private ArrayList<String> link;
    LayerOne(){
       this.link = new ArrayList();
       
    }
    LayerOne(ArrayList<String> a ){
        this.link = a;
    }
    public ArrayList<String> get(){
        return this.link;
    }
    
    public void LayerTwoToDo() throws IOException, JSONException{
        ArrayList<ArrayList> tmp = new ArrayList();
        LayerTwo lyt = new LayerTwo();
        LayerThree lyth = new LayerThree();
        Book bk = new Book();
        lyt.getLinkLayerTwo(this.link);
        ArrayList s = new ArrayList();
        s = lyt.get();


        lyth.layerThreeGetLinkAllObject(s);

        tmp = lyth.get();
        for( int i = 0; i < tmp.size(); i++){
            bk.getComment(tmp.get(i));
        } 
    }
    
   public void getAllLink(String url) throws IOException{
        ArrayList<String> tmp = new ArrayList();
        if(!url.contains("http://")){
            url = "http://" + url;
        }
        try{
            Document doc = Jsoup.connect(url).method(Method.POST)
                    .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                    .timeout(100*1000)
                    .ignoreHttpErrors(true)
                    .get(); 
            Elements elements = doc.select("div[class=\"header-navigation-mobile\"]");
            Elements e = elements.select("a[href]");
            for(Element ls : e){
               tmp.add(ls.attr("href"));
            }
            tmp.remove(14);
            String lp = tmp.get(5);
            tmp.remove(5);
            tmp.add(lp);
            this.link = tmp;
        }catch(SocketException s){
            System.out.println("Socket Exception");
        }catch (NullPointerException e){
            System.out.println("Null Pointer Exception");
        }
        catch (HttpStatusException e){
            System.out.println("Http Status Exception");
        }
    }

   
 }
