package web2json.tag;

import java.util.HashMap;
import java.util.ArrayList;

public class Tag {
		public String tag;
		public String tagname;
		public String inside="";
		public HashMap<String, String> attr=new HashMap<String, String>();
		public ArrayList<Tag> children=new ArrayList<Tag>();
		public Tag parent;
		public String id;
		public String _class;
		public String text="";
		public boolean ctx=false;

	
}
