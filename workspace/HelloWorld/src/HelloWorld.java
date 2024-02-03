import java.util.*;

/**
 * @author <a href="mailto:palmer.sr@gmail.com">Scott Palmer</a>
 * 
 *         Run commands in directory ~/workspace/HelloWorld/
 * 
 *         This project has an Ant buld file which contains a run command
 *         "run HelloWorld". You just use the `ant` command to compile the
 *         code and run with `ant "run HelloWorld"`
 * 
 *         Compile with:
 *         javac -g .\src\*.java -d .\bin\
 *         Run with:
 *         java -cp .\bin\ HelloWorld
 */

public class HelloWorld {
	public static void main(String[] args) {
		System.out.println("Hello World");
	}
}
