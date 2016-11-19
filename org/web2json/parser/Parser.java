package  web2json.parser;

import java.util.ArrayList;

import web2json.document.Document;
import web2json.json.JsonCreate;
import web2json.tag.Tag;

public class Parser {
	private static Parser instance =null;
	private Parser(){
		
	}
	
	public static Parser getParser(){
		if(instance==null){
			instance= new Parser();
			return instance;
		}else{
			return instance;
		}
	}
	
	
	
	
	
	public String removeDoctype(String html){
	char[] buff=html.toCharArray();
	boolean tagstart=false;
	String tag="";
	ArrayList<String> doctype=new ArrayList<String>();
	System.out.println("inside doctype removal prototype");
	for(int i=0;i<buff.length;i++){
		if(buff[i]=='<'){
			tagstart=true;
		}
		else if(tagstart&&buff[i]!='>'){
			tag=tag+buff[i];
		}else if(tagstart&&buff[i]=='>'){
			tagstart=false;
			if(tag.contains("DOCTYPE")||tag.contains("doctype")||tag.startsWith("!")||tag.startsWith("<!")){
				doctype.add("<"+tag+">");
				System.out.println("lets break a leg");
				break;
			}
		}
		
	}
	for(String hp:doctype){
		html=html.replace(hp, "");
		System.out.println("chop chopped");
	}
		return html;
	}

	
	
	public Tag parse(Document doc){
		 String html=doc.html;
		 System.out.println(html);
		 html=removeComment(html);
		Tag root=new Tag();
		root.parent=null;
		Tag current=root; 
		String inside="",tag="";
		boolean tagstart=false;
		char buff[]=html.toCharArray();
		System.out.println(buff[530]+""+buff[531]+buff[532]+""+buff[533]+buff[534]+""+buff[535]+buff[536]+""+buff[537]+buff[538]+""+buff[539]+buff[540]+""+buff[541]+buff[542]+""+buff[543]+buff[544]+""+buff[545]+buff[546]+""+buff[547]+buff[548]+""+buff[549]+buff[550]+""+buff[551]+buff[552]+""+buff[553]+buff[554]+""+buff[555]);
		for(int i=0;i<buff.length;i++){
			if(buff[i]=='<'){
				tagstart=true;
			if(inside!=""||!inside.isEmpty()){
				try{
					current.inside=inside;
					current.text=inside;
					
				}catch(Exception e){
					System.out.println(i);
				}
					
				}
			}
			else if(tagstart&&buff[i]=='>'){
				tagstart=false;
				if(tag.contains("/")){
					System.out.println(current.inside+inside);
						if(current.inside!=" "&&inside==""){
							System.out.println(i);
							current.inside=current.inside;
							current.text=current.inside;
						}else{
							current.inside=inside;
							current.text=current.inside;
								
						}	
					
					current=current.parent;
				}else{					Tag newTag=new Tag();
					newTag.parent=current;
					current.children.add(newTag);
					current=newTag;
					current.tagname=tag.split(" ")[0];
					String temp=tag.replace(current.tagname, "");
					String attri[]=temp.split(" ");
					for(int ip=0;ip<attri.length;ip++){
						if(!(attri[ip].endsWith("'")||attri[ip].endsWith("\""))){
							try{
								attri[ip+1]=attri[ip]+" "+attri[ip+1];
							}catch(Exception e){
								continue;
							}
						}else{
							String key=attri[ip].split("=")[0];
							String value=attri[ip].split("=")[1];
							key.replace(" ", "");
							current.attr.put(key, value);
							
						}
					}
					current.id=current.attr.get("id");
					current._class=current.attr.get("class");
				}
				tag="";
				inside="";
				
			}
			else{
				 if(tagstart){
					 tag=tag+buff[i];
					 
				 }else{
					 inside=inside+buff[i];
				 }
			}
		}
		
		

		return root;
	}

	public String removeComment(String html) {
		ArrayList<String>comment=new ArrayList<String>();
		char buff[]=html.toCharArray();
		String tmp="";
		boolean com=false;
		for(int i=3;i<buff.length;i++){
			if(buff[i]=='-'&&buff[i-1]=='-'&&buff[i-2]=='!'&&buff[i-3]=='<'){
			com=true;
			}
			else if(com){
				tmp=tmp+buff[i];
			}
			if(buff[i]=='-'&&buff[i+1]=='-'&&buff[i+2]=='>'){
				com=false;
				comment.add(tmp);
				tmp="";
			}
		}
		html=html.replace("<!--", "");
		html=html.replace("-->", "");
	 for(String ch:comment){
		 html=html.replace(ch, "");
		 
	 }
		return html;
		
		
		
	}



	public void parsetree(Tag n){
		System.out.println("started parsing");
		if(!n.children.isEmpty()){
			for(Tag a:n.children){
				System.out.println("parent is"+a.parent.tagname);
				System.out.println("tagname " +a.tagname);
				System.out.println("text: " +a.inside);
				System.out.println("attr "+a.attr);
				System.out.println("id "+a.id);
				System.out.println("class "+a._class);
				System.out.println("\n\n");
				parsetree(a);
				
		}
		}
		
	}
	
	public String json(Tag root){
		String json;
		JsonCreate js=JsonCreate.getJsonCreateInstance();
		json=js.getJson(root);
		
		return json;
	}
	
	}




