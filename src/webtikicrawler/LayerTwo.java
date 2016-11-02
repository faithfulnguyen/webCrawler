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
public class LayerTwo {
    private ArrayList linkLayerTwo;
    
    public LayerTwo(){
        this.linkLayerTwo = null;
    }
    
    public void getLinkLayerTwo(ArrayList<String> url) throws IOException{
        ArrayList tmp = new ArrayList();
        tmp = getAllLinkLayerTwo(url);
        linkModify(tmp);
        String f = laptopProcess(url.get(url.size() - 1));
        this.linkLayerTwo.add(f);
        
    }
    
    
    public String laptopProcess(String url) throws IOException{
        try{
            Document doc = Jsoup.connect(url).method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                    .timeout(100 * 1000)
                    .ignoreHttpErrors(true)
                    .get();
            Elements el = doc.select("div[class=\"list-group\"]");
            Element a = el.select("a[href]").first();
            String lp = a.attr("href");
            return lp;
        }catch(UnknownHostException e){
            System.out.println("error connect");
        }
        catch (NullPointerException e){
            System.out.println("error connect");
        }
        catch (HttpStatusException e){
            System.out.println("error connect");
        }
        catch (SocketTimeoutException e){
            System.out.println("time out");
        }
        return null;
    }
    
    // một mảng ArrayList, với từng phần tử là một ArrayList, trong từng
    // phần tử đó chứa một mảng các chuỗi là các link.
    public ArrayList getAllLinkLayerTwo(ArrayList<String> url)throws IOException{
        ArrayList lk = new ArrayList();
        for (int i = 0; i < url.size() - 1; i++){
            ArrayList<String> tmp = new ArrayList();
            tmp = getLinkLayerTwoOneLink(url.get(i));
            lk.add(tmp);
        }
        return lk;
    }
    
    public ArrayList<String> getLinkLayerTwoOneLink(String url) throws IOException{  
        ArrayList<String> tmpUrl = new ArrayList();
        try{
            Document doc = Jsoup.connect(url).method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                    .timeout(100*1000)
                    .ignoreHttpErrors(true)
                    .get();

            Elements el = doc.select("div[class=\"list-group js-cat-nav\"]");
            Elements a = el.select("a[href]");
            for(Element e : a){
                tmpUrl.add(e.attr("href"));
            }
            return tmpUrl;
        }catch(UnknownHostException e){
            System.out.println("error connect");
        }
        catch (NullPointerException e){
            System.out.println("error connect");
        }
        catch (HttpStatusException e){
            System.out.println("error connect");
        }
        catch (SocketTimeoutException e){
            System.out.println("time out");
        }
        return tmpUrl;
    }
    
    public void linkModify(ArrayList<ArrayList> url){
        url.get(0).remove(0);
        ArrayList tp = new ArrayList();
        for(int i = 0; i < url.size(); i++){
            tp.add(url.get(i).get(0));
        }
        this.linkLayerTwo = tp;
    }
    
    public ArrayList get(){
        return this.linkLayerTwo;
    }
    
}
