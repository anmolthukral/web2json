package web2json.parser;

import web2json.document.Document;
import web2json.tag.Tag;

public class SimpleParser {
	
		public Tag parser(Document doc){
			Parser p=Parser.getParser();
			String html=p.removeDoctype(doc.html);
			html=p.removeComment(html);
			
			Tag root=new Tag();
			Tag current=root;
			root.parent=null;
			String inside="",tag="";
			boolean tagstart=false;
			char buff[]=html.toCharArray();
			for(int i=0;i<buff.length;i++){
				if(buff[i]=='<'){
					tagstart=true;
					Tag temp=new Tag();
					temp.parent=current;
					System.out.println("parent is :"+temp.parent);
					temp.ctx=true;
					current.children.add(temp);
					current=temp;
					
				}else if(tagstart==true&&buff[i]!='>'){
					tag=tag+buff[i];
				}else if(tagstart==false&&((buff[i]!='>')||(buff[i]!='<'))){
					current.inside=current.inside+buff[i];
				}else if(tagstart==true&&buff[i]=='>'){
					tagstart=false;
					if(tag.startsWith("/")||tag.endsWith("/")){
						current.ctx=false;
						current=current.parent;

						System.out.println("back to parent"+ current);
						
					}else{
						current.tagname=tag.split(" ")[0];
						String temp=tag.replace(current.tagname, "");
						String attr[]=temp.split(" ");
						for(int ip=0;ip<attr.length;ip++){
							if(!(attr[ip].endsWith("'")||attr[ip].endsWith("\""))){
								try{
									attr[ip+1]=attr[ip]+" "+attr[ip+1];
								}catch(Exception e){
									continue;
								}
							}else{
								try{
									String key=attr[ip].split("=")[0];
									String value=attr[ip].split("=")[1];
									key.replace(" ", "");
									current.attr.put(key, value);
								
								}catch(Exception e ){
									System.out.println("caught an exception at hashmap\n tag ="+ tag);
									continue;
								}
									}
						}
						current.id=current.attr.get("id");
						current._class=current.attr.get("class");
								
					}
					inside="";
					tag="";
				}
				
			}
			
			
			return root;
		}
}
