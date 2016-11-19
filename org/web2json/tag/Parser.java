package web2json.tag;

import java.util.ArrayList;
import java.util.HashMap;


public class Parser {
		
	
	


 Tag parse(String html){
	 System.out.println(html);
	Tag root=new Tag();
	root.parent=null;
	Tag current=root; 
	String inside="",tag="";
	boolean tagstart=false;
	char buff[]=html.toCharArray();
	for(int i=0;i<buff.length;i++){
		if(buff[i]=='<'){
			tagstart=true;
		if(inside!=""||!inside.isEmpty()){
			System.out.println("writing from the if"+inside);
			current.inside=inside;
		}
		}
		else if(tagstart&&buff[i]=='>'){
			tagstart=false;
			if(tag.contains("/")){
				if(current.inside!=""&&inside==""){
					current.inside=current.inside;
				}else{
					current.inside=inside;
						
				}
				current=current.parent;
				System.out.println(inside);
				System.out.println(tag);
			}else{
				System.out.println(tag);
				Tag newTag=new Tag();
				newTag.parent=current;
				current.children.add(newTag);
				current=newTag;
				current.tagname=tag.split(" ")[0];
				String temp=tag.replace(current.tagname, "");
				String attri[]=temp.split(" ");
				for(int ip=0;ip<attri.length;ip++){
					if(!attri[ip].endsWith("'")){
						try{
							attri[ip+1]=attri[ip]+" "+attri[ip+1];
						}catch(Exception e){
							continue;
						}
					}else{
						String key=attri[ip].split("=")[0];
						String value=attri[ip].split("=")[1];
						current.attr.put(key, value);
						
					}
				}
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

private String removeComment(String html) {
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



	public static void main(String[] args){
		 Parser p=new Parser();
		 Tag n=p.parse("<h1 src='name' data src='booth' class='june moon' id='mayhem'> <h2>helloworld <h2> Lorem ipsum </h2></h2><h2>helloworld2</h2><p>hi anmol</p> <ul><li>apple</li><li>maggo</li><h3 class='wavin flag'>helloworld <h4> Lorem ipsum </h4></h3></h1>");
		 p.parsetree(n);
	}
public void parsetree(Tag n){
	System.out.println("started parsing");
	if(!n.children.isEmpty()){
		for(Tag a:n.children){
			System.out.println("parent is"+a.parent.tagname);
			System.out.println("tagname " +a.tagname);
			System.out.println("text: " +a.inside);
			System.out.println("attr "+a.attr);
			System.out.println("\n\n");
			parsetree(a);
			
	}
	}
	
}

}



