package unitTest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import search.MainSearch;
public class UnitTests {

	private static String expected;
	
	
	
	@Test
	public void searchInSearchtestFolderOnlyPomXML() throws IOException {
		String[] args = {"-f","pom.xml","searchtest"};
		MainSearch.main(args);
		expected="searchtest\\pom.xml"+
		"\n"+"searchtest\\src\\test\\java\\test\\folder\\pom.xml"+"\n";
		assertEquals(expected, MainSearch.output);
			
	}
	@Test
	public void searchInvalidParameterNumber() throws IOException {
		String[] args = {"-f","pom.xml"};
		MainSearch.main(args);
		expected="Invalid amount of arguments and values";
		assertEquals(expected, MainSearch.output);
			
	}
	@Test
	public void searchTooMuchParameters() throws IOException {
		String[] args = {"-f","pom.xml", "searchtest", "-p", "testword", "-t"};		
		MainSearch.main(args);
		expected="Invalid amount of arguments and values";
		assertEquals(expected, MainSearch.output);
	}
	@Test
	public void searchUnrealText() throws IOException {
		String[] args = {"-f","pom.xml", "searchtest", "-p", "testworda"};		
		MainSearch.main(args);
		expected="";
		assertEquals(expected, MainSearch.output);		
	}
	
	@Test
	public void searchEmptyParameters() throws IOException {
		String[] args = {};		
		MainSearch.main(args);
		expected="Invalid amount of arguments and values";
		assertEquals(expected, MainSearch.output);
	}	
	
	
}
