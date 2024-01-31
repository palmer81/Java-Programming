import java.util.ArrayList;

public class KeyObjects {
	public ArrayList<String> primaryKey(String attribute) {
		ArrayList<String> attrNames = new ArrayList<String>();
		int indexOfLastComma = 0;
		for (int i = 0; i < attribute.indexOf(i); i++) {
			if (attribute.indexOf(i) == ',') {
				attrNames.add(attribute.substring(indexOfLastComma, i).trim());
				indexOfLastComma = i;
			}
		}
		if (attrNames.isEmpty())
			attrNames.add(attribute.trim());
		else
			attrNames.add(attribute.substring(indexOfLastComma).trim());
		
		return attrNames;
	}
	
	public ArrayList<String> foreingKey(String attribute, String tableName) {
		ArrayList<String> attrNames = new ArrayList<String>();
		int indexOfLastComma = 0;
		for (int i = 0; i < attribute.indexOf(i); i++) {
			if (attribute.indexOf(i) == ',') {
				attrNames.add(attribute.substring(indexOfLastComma, i).trim());
				indexOfLastComma = i;
			}
		}
		if (attrNames.isEmpty())
			attrNames.add(attribute.trim());
		else
			attrNames.add(attribute.substring(indexOfLastComma).trim());
		
		return attrNames;
	}
}