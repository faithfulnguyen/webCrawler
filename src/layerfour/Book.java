/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package layerfour;

import java.io.IOException;
import static java.lang.System.exit;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import org.json.JSONException;
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


// input của tầng này sẽ là một ArrayList
public class Book {
    private ArrayList<ArrayList<String>> linkbook;
    
    
    public Book(){
        this.linkbook = null;
        
    }
    
    //nhân vào input là một ArrayList String gồm các link trong tầng 4
    public void getComment( ArrayList<String> url )throws IOException, JSONException{
       // gỡ bỏ sách hay tiki khuyên đọc
       url.remove(0);
       url.remove(2);
       ArrayList<String> frontier = new ArrayList();
       // thêm trực tiếp đường dẫn đến sách hay tiki khuyên đọc
      String col = "allComment";
       url.add("van-hoc-viet-nam/c4148");   
       for ( int i = 0; i < url.size(); i++){
           this.linkbook = bookGetLinkAllPage(url.get(i));
            for( int j = 0; j < this.linkbook.size(); j++){
                for( int k = 0; k < this.linkbook.get(j).size(); k++){  
                    if(frontier.contains(this.linkbook.get(j).get(k)) == true){
                    } else{
                        // Crawler cr = new Crawler();
                        Crawler cr = new Crawler();
                        cr.getComment(this.linkbook.get(j).get(k), col);
                        frontier.add(this.linkbook.get(j).get(k));
                    }
                }
           }
           this.linkbook.clear();
       }
    }
 
    public ArrayList bookGetLinkAllPage(String url) throws IOException{   
        String np = "http://tiki.vn" + url + "?page=" + (1);
        int numPage =  getNumberPage(np);
        ArrayList lk = new  ArrayList();
        for ( int i = 0; i < numPage; i++ ){
            np = "http://tiki.vn" + url + "?page=" + (i + 1);
            ArrayList tmp = new  ArrayList();
            tmp =  bookGetLinkOnePage(np);
            if(tmp != null){                 
                lk.add(tmp);              
            }            
        }
        return lk;
    }
   
    public int getNumberPage(String url) throws IOException{
        int numberPage = 0;
        try{
            Document doc = Jsoup.connect(url).method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                    .timeout(100*1000)
                    .ignoreHttpErrors(true)
                    .get();
            Elements ele = doc.select("div[class=\"filter-list-box\"]");
            String f = ele.select("h4").text();

            f = f.substring(1, f.length()-1);
            int i = Integer.parseInt(f);
            numberPage = i / 24;
            float mod = i % 24;
            if (mod != 0){
                numberPage += 1;
            }

            //return numberPage;
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
        return numberPage;
    }
  
    // mỗi phần tử trong này là một cuốn sách.
    // lấy sách từ một page
    public ArrayList bookGetLinkOnePage(String url) throws IOException{
        ArrayList al = new ArrayList();
        try{
            Document doc = Jsoup.connect(url).method(Connection.Method.POST)
                    .userAgent("Mozilla/5.0 (Window NT 6.0 ) Chrome/19.0.1084.46 Safari/ 536.5")
                    .timeout(100*1000)
                    .ignoreHttpErrors(true)
                    .get();
            Elements ele = doc.select("div[class=\"product-box-list\"]");
            Elements href = ele.select("a[href]");
              for(Element e : href){
                  al.add(e.attr("href"));
              }
            //return al;
        }
        catch(UnknownHostException e){
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
        catch(Exception e){
            e.printStackTrace();
        }
        return al;
    }
    
}
