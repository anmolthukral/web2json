package web2json.json;

import web2json.tag.Tag;

public class JsonCreate {
	public static JsonCreate instance=null;
	private JsonCreate(){
		
	}
	
	public static JsonCreate getJsonCreateInstance(){
		if(instance==null){
			instance= new JsonCreate();
		}
		return instance;
	}
	
	public String getJson(Tag root){
		String json="{";
		json=json+"tag : " +root.tagname+", text : "+root.text+", attr :[ ";
		for(String str:root.attr.keySet()){
			json=json+"{ "+ str+" : "+root.attr.get(str) +" },";
		}
		json=json.substring(0, json.length()-1);
		json=json+" ], children : [ ";
		if(!root.children.isEmpty()){
			for(Tag x:root.children){
				json=json+getJson(x);
			}
		}
		json=json+" ] }";
		return json;
	}
}
