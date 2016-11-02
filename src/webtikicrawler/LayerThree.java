/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webtikicrawler;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Trung_Tin
 */
public class LayerThree {
    private ArrayList link;
    
    public LayerThree(){
        this.link = null;
    }
    public ArrayList<ArrayList> get(){
        return this.link;
    }
    public ArrayList<String> layerThreeGetLink(String url) throws IOException{
        ArrayList<String> tmpUrl = new ArrayList();
        String lik = "http://tiki.vn" + url;
        try{
        Document doc = Jsoup.connect(lik).method(Connection.Method.POST)
                .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                .timeout(100*1000)
                .ignoreHttpErrors(true)
                .get();
        Elements el = doc.select("div[class=\"list-group\"]");
        Elements a = el.select("a[href]");
        for(Element e : a){
            String tmp = e.attr("href");
            if(tmp.equalsIgnoreCase("javascript:void(0)") == false ){
                tmpUrl.add(tmp);
            }            
        }
        }catch(UnknownHostException e){
            System.out.println("error connect");
        }catch (NullPointerException e){
            System.out.println("error connect");
        }catch (HttpStatusException e){
            System.out.println("error connect");
        }catch (SocketTimeoutException e){
            System.out.println("time out");
        }catch(Exception e){
            e.printStackTrace();
        }
        return tmpUrl;
    }
    // output là một ArrayList, với mỗi element trong ArrayList là một
    // link của một đối tượng trong 15 đối tượng, xử lý cho laptop sau
    
    public void layerThreeGetLinkAllObject(ArrayList<String> url) throws IOException{
        ArrayList al = new ArrayList();
        for( int i = 0; i < url.size(); i++){
            ArrayList tmp = new ArrayList();
            tmp = layerThreeGetLink(url.get(i));
            
            al.add(tmp);
        }
        this.link = al; 
    }
    
}
