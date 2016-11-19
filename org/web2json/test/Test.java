package web2json.test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.lang.model.util.SimpleAnnotationValueVisitor6;

import web2json.connect.Connection;
import web2json.document.Document;
import web2json.parser.Parser;
import web2json.parser.SimpleParser;
import web2json.tag.Tag;

public class Test {
public static void main(String[] args){
	try {
		System.out.println("initiating launch procedure");
		System.out.println("status OK");
		System.out.println("trying to build a connection");
		Document doc=Connection.makeConnection("https://www.facebook.com/");
		if(doc!=null){
			System.out.println("connection build succesful");
		}
		else{
			System.out.println("error in building connection");
		}
		System.out.println(doc.html);
		Parser p=Parser.getParser();	
		SimpleParser parser=new SimpleParser();
		
		
		System.out.println(doc);
		
		Tag root2=parser.parser(doc);
		
		
		
		System.out.println("********************JSON**************************");
		String json2=p.json(root2);
		System.out.println(json2);
		File f=new File("json.out");
		FileWriter fw=new FileWriter(f);
		fw.append(json2);
		System.out.println("***************************************************");
		
		System.out.println(doc.html.length());
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}
