import java.util.*;

public class Product {
	
	
	ArrayList<String> keys = new ArrayList<String>();
	ArrayList<String> values = new ArrayList<String>();
	
	public Product() {
		
	}
	
	public void addField(String fieldName, String fieldValue) {
		keys.add(fieldName);
		values.add(fieldValue);
	}
	
	public void replace(String fieldName, String fieldValue) {
		int index = keys.indexOf(fieldName);
		values.remove(index);
		values.add(index, fieldValue);
	}
	
	public String getValue(String fieldName) {
		int index = keys.indexOf(fieldName);
		return values.get(index);
	}
	
	
	public String getKey(String valueName) {
		int index = values.indexOf(valueName);
		return keys.get(index);
	}
	
	public String toString() {
		String output = "";
		
		for(int i = 0; i < keys.size(); i++) {
			output += keys.get(i) + ": " + values.get(i) + "\n";
		}
		
		return "-------------------\n" + output + "-------------------\n";
	}
}
