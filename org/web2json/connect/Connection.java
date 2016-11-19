package web2json.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import web2json.document.Document;

public class Connection {
URL url;
public Connection(String url) throws MalformedURLException{
	this.url=new URL(url);
	}
public String connect() throws IOException{
	String result="";
	URLConnection conn = this.url.openConnection();
	conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36");
    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    String inputLine;
    while((inputLine=br.readLine())!=null){
    	result=result+"\n"+inputLine;
    }
    br.close();
    
	return result;
}

public static Document makeConnection(String url) throws IOException{
	Connection con=new Connection(url);
	String result=con.connect();
	return new Document(result);
}
}

