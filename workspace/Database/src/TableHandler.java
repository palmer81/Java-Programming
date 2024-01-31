import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
   This program applies line numbers to a file.
 */
public class TableHandler
{
	public static void main(String[] args) throws IOException
	{
		// Prompt for the input and output file names
		TableHandler any = new TableHandler("hello");
		any.createTableFile();
		Scanner console = new Scanner(System.in);
		System.out.print("Input file: ");
		String inputFileName = console.next();
		System.out.print("Output file: ");
		String outputFileName = console.next();

		// Construct the Scanner and PrintWriter objects for reading and writing

		File inputFile = new File(inputFileName);
		Scanner in = new Scanner(inputFile);
		PrintWriter out = new PrintWriter(outputFileName);
		int lineNumber = 1;

		// Read the input and write the output

		while (in.hasNextLine())
		{
			String line = in.nextLine();
			out.println("/* " + lineNumber + " */ " + line);
			lineNumber++;
		}

		in.close();
		out.close();
	}

	/**
	 * Constructor creates FileHandler Objects
	 * @throws IOException 
	 */
	public TableHandler(String tableName) {
		tableName +=".table";
		tableFile = new File(tableName);
	}

	/**
	 * @throws IOException
	 */
	//TODO: create interface for found file
	public void createTableFile() throws IOException {
		boolean fileExists = tableFile.exists();
		if (!fileExists) {
			tableFile.createNewFile();
		}
		else
			System.out.println("this table exists already");
	}
	
	/**
	 * @param tableName
	 */
	public void removeTableFile(String tableName) {
		File deleteTableFile = new File(tableName+".table");
		boolean fileExists = tableFile.exists();
		if (fileExists) {
			deleteTableFile.delete();
		}
		else
			System.out.println("this table does not exist");
	}
	
	/**
	 * @param lineEntry
	 * @throws FileNotFoundException 
	 */
	public void writeToTable(File tableFile, String lineEntry) throws FileNotFoundException {
		PrintWriter toFile = new PrintWriter(tableFile);
		toFile.println(lineEntry);
		toFile.close();
	}
	
	/**
	 * @param tableName
	 * @return
	 */
	public String lineEntryTableName(String tableName) {
		tableName = "<TABLE "+tableName+">";
		return tableName;
	}
	
	/**
	 * @param queryName
	 * @return
	 */
	public String lineEntryQueryName(String tableName) {
		tableName = "<QUERY "+tableName+">";
		return tableName;
	}
	
	/**
	 * @param attribute
	 * @param dataType
	 * @return
	 */
	public String lineEntryAttributeSet(String attribute, String dataType) {
		attribute = "/t<ATTRIBUTE "+attribute.toLowerCase()+" "+
					dataType.toUpperCase()+">";
		return attribute;
	}
	
	/**
	 * @param element
	 * @return
	 */
	public String lineEntryElementString(String element) {
		element = "/t/t"+element;
		return element;
	}
	
	/**
	 * @param constraint
	 * @return
	 */
	public String lineEntryConsraintString(String constraint) {
		constraint = "/t"+constraint;
		return constraint;
	}

	String tableName;
	File tableFile;
}
