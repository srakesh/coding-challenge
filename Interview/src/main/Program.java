package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


import org.json.simple.parser.ParseException;
import org.json.simple.parser.ContainerFactory;
import org.json.simple.parser.JSONParser;
/**
 * Interview Program to process a json file and print flattened 
 * key ("dot" separated)/value pairs.
 * @author rakeshsharma
 *
 */
public class Program {
	/**
	 * Use a Trie data structure to store:-
	 * key as stem
	 * value as node
	 * A list of Trie Nodes as children
	 */
	class TrieNode {
		private String key; // Stem
		private Object value; // Leaves. Leaf Value is null if a node has children
		private List<TrieNode> children;
		
		public TrieNode(String key, Object val) {
			this.key = key;
			this.value = val;
			this.children = null;
		}
		
		public void addChildNode(TrieNode node) {
			if (children == null) {
				children = new ArrayList<TrieNode>();
			}
			children.add(node);
		}
		
		public List<TrieNode> getChildren(){
			return this.children;
		}
		
		public String getKey() {
			return this.key;
		}
		
		public Object getValue() {
			return this.value;
		}
	}
	private static final String rootKey = ""; // Root key may be an empty string
	private TrieNode root = new TrieNode(rootKey, null);
	
	/**
	 * Main method - Reads json file and calls build/printOutput Trie structure
	 * @param args
	 * @throws Exception for malformed JSON, File not Found etc.
	 */
	public static void main(String args[]) throws Exception {
		if (args.length <= 0 || args.length > 1) {
			printUsage();
		}
		
		File file = new File(args[0]);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file)); 
			String line;
			StringBuilder buf = new StringBuilder();
		    while ((line = br.readLine()) != null) {
		      buf.append(line); 
		    } 
		
			Program p = new Program();
			
			p.build(buf.toString());
			p.printOutput();
		}
		finally {
			if (br != null) br.close();
		}
	}
	
	/**
	 * Builds a Trie structure from json input
	 * @param input
	 * @return None
	 */
	public void build(String input) throws ParseException {
		
		if (input == null || "".equals(input)) return;
		
		ContainerFactory orderedKeyFactory = new ContainerFactory()
		{
		    public List creatArrayContainer() {
		      return new LinkedList();
		    }

		    public Map createObjectContainer() {
		      return new LinkedHashMap();
		    }

		};
		
		JSONParser parser = new JSONParser();
		LinkedHashMap jsonObject = (LinkedHashMap)parser.parse(input, orderedKeyFactory);
		
		@SuppressWarnings("unchecked")
		Iterator<String> keys = jsonObject.keySet().iterator();

		while (keys.hasNext()) {
		    String key = keys.next();
		    Object obj = jsonObject.get(key);
		    TrieNode n = buildTrie(key, obj);
		  
		    root.addChildNode(n);
		    
		}
		
	}
	
	/**
	 * Print the output json string
	 * @return None
	 */
	public void printOutput() {
		System.out.println(getOutput());
	}
	
	/**
	 * Get the output json string
	 * @return None
	 */
	public StringBuilder getOutput() {
		StringBuilder buf = new StringBuilder();
		buf.append("{\n");
		runDepthFirstSearch(root.getKey(), root, buf);
		buf.append("}");
		
		return buf;
	}
	
	
	/**
	 * Retrieves the Trie key/values by running Depth First Search
	 * @param parentPath contains concat key values up to root
	 * @param TrieNode
	 * @return None
	 */
	public void runDepthFirstSearch(String parentPath, TrieNode n, StringBuilder buf) {
		if (n == null) return;
		
		if (n.getValue() != null) {
			buf.append(" " + parentPath + "\"");
			buf.append(":" + n.getValue() + "\n");
		}
		List<TrieNode> children = n.getChildren();
		String childKeyPath;
		if (children != null) {
			for (TrieNode child: children) {
				if (!parentPath.equalsIgnoreCase("")) {
					childKeyPath = parentPath + "." + child.getKey();
				} else {
					childKeyPath = "\"" + child.getKey();
				}
				
				runDepthFirstSearch(childKeyPath, child, buf);
			}
		}
		
	}
	
	/**
	 * Function to build Trie structure
	 * @param key 
	 * @param obj JSONObject
	 * @return Parent Trie Node
	 */
	private TrieNode buildTrie(String key, Object obj) {
		if (obj instanceof LinkedHashMap) {
	        // Value is LinkedHashMap/JSON Object. Recursively add json object 
	    	TrieNode parent = new TrieNode(key, null);

			Iterator<String> keys = ((LinkedHashMap)obj).keySet().iterator();

			while(keys.hasNext()) {
				String keyChild = keys.next();
			    parent.addChildNode(buildTrie(keyChild, ((LinkedHashMap)obj).get(keyChild)));
			}
			return parent;
	    } else {
	    	// Value is a single string value. Need to surround with quotes 
	    	if (obj instanceof String) {
	    		return new TrieNode(key, "\"" + obj.toString() + "\"");
	    	} else { // All others - int, double, boolean etc
	    		return new TrieNode(key, obj);
	    	}
	    }
		
	}
	

	/**
	 * Function which prints usage.
	 * @param None
	 * @return None
	 */
	public static void printUsage() {
		System.out.println("Usage:-");
		System.out.println("java -jar ./Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar <json file>");
		System.exit(1);
	}
	

}
