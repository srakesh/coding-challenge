/**
 * 
 */
package test;

import java.io.FileNotFoundException;

import org.json.simple.parser.ParseException;

import junit.framework.TestCase;

import main.Program;

/**
 * @author rakeshsharma
 * Junit Test Case for main.Program
 */
public class ProgramTest extends TestCase {
	
	/**
	 * Test for empty or null input json string. No output.
	 * Program should not throw Exception.
	 * 
	 */
	public void testEmptyString() {
		Program p = new Program();
		boolean thrown = false;
		String input = "";
		try {
			p.build(input);
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
		
	}
	 
	/**
	 * Test for empty json. No output.
	 * @throws Program should not throw exception
	 */
	public void testEmptyJson() {
		Program p = new Program();
		boolean thrown = false;
		String input = "{}";
		try {
			p.build(input);
		} catch(Exception e) {
			thrown = true;
		}
		assertFalse(thrown);
	}
	
	/**
	 * Test for badly formed json. Program should throw ParseException
	 * @throws ParseException
	 */
	public void testInValidJson() {
		boolean thrown = false;
		Program p = new Program();
		String input = "{\n" + 
				"    \"a\": \n" +  // Value missing here
				"    \"a\": true,\n" + 
				"    \"c\": {\n" + 
				"        \"c\": 3,\n" + 
				"        \"e\": {\n" + 
				"           \"e\" : \"test\"\n" + 
				"         }\n" + 
				"    }\n" + 
				"}";
		
		try {
			p.build(input);
		} catch(ParseException e) {
			thrown = true;
		}
		
		assertTrue(thrown);
		
	}
	
	/**
	 * Test for invalid json file path
	 * @throws FileNotFoundException
	 */
	public void testInValidFilePath() {
		boolean thrown = false;
		String [] args = {"//Invalid//Path//json.txt"};
		
		
		try {
			Program.main(args);	
		} catch(FileNotFoundException e) {
			thrown = true;
		} catch(Exception e) {
			thrown = false;
		}
		
		assertTrue(thrown);
		
	}
	
	/**
	 * Test a valid json input from Example
	 * {
		    "a": 1,
		    "b": true,
		    "c": {
		        "d": 3,
		        "e": "test"
		    }
       }
	 * @throws ParseException
	 */
	public void testValidJsonFromExample() throws ParseException{
		Program p = new Program();
		String input = "{\n" + 
				"    \"a\": 1,\n" + 
				"    \"b\": true,\n" + 
				"    \"c\": {\n" + 
				"        \"d\": 3,\n" + 
				"        \"e\": \"test\"\n" + 
				"    }\n" + 
				"}";
		String expectedOut = "{\n" + 
				" \"a\":1\n" + 
				" \"b\":true\n" + 
				" \"c.d\":3\n" + 
				" \"c.e\":\"test\"\n" + 
				"}";
		
		p.build(input);
		assertEquals(p.getOutput().toString(), expectedOut);
		
	}
	
	public void testValidJsonFromExampleWithFloats() throws ParseException{
		Program p = new Program();
		String input = "{\n" + 
				"    \"a\": 1.0,\n" + 
				"    \"b\": true,\n" + 
				"    \"c\": {\n" + 
				"        \"d\": 3.345,\n" + 
				"        \"e\": \"test\"\n" + 
				"    }\n" + 
				"}";
		String expectedOut = "{\n" + 
				" \"a\":1\n" + 
				" \"b\":true\n" + 
				" \"c.d\":3\n" + 
				" \"c.e\":\"test\"\n" + 
				"}";
		
		p.build(input);
		assertEquals(p.getOutput().toString(), expectedOut);
		
	}
	
	/**
	 * Test a more complex input Json
	 *       {
			    "glossary": {
			        "title": "example glossary",
			        "GlossDiv": {
			                "title": "S",
			                "GlossList": {
			                "GlossEntry": {
			                    "ID": "SGML",
			                    "SortAs": "SGML",
			                    "GlossTerm": "Standard Generalized Markup Language",
			                    "Acronym": "SGML",
			                    "Abbrev": "ISO 8879:1986",
			                    "GlossDef": {
			                         "para": "A meta-markup language, used to create markup languages such as DocBook.",
			                         "GlossSeeAlso": "GML"
			                    },
			                    "GlossSee": "markup"
			                }
			            }
			        }
			    }
			}
	 * @throws ParseException
	 */
	public void testComplexValidJson() throws ParseException{
		Program p = new Program();
		String input = "{\n" + 
				"    \"glossary\": {\n" + 
				"        \"title\": \"example glossary\",\n" + 
				"        \"GlossDiv\": {\n" + 
				"                \"title\": \"S\",\n" + 
				"                \"GlossList\": {\n" + 
				"                \"GlossEntry\": {\n" + 
				"                    \"ID\": \"SGML\",\n" + 
				"                    \"SortAs\": \"SGML\",\n" + 
				"                    \"GlossTerm\": \"Standard Generalized Markup Language\",\n" + 
				"                    \"Acronym\": \"SGML\",\n" + 
				"                    \"Abbrev\": \"ISO 8879:1986\",\n" + 
				"                    \"GlossDef\": {\n" + 
				"                         \"para\": \"A meta-markup language, used to create markup languages such as DocBook.\",\n" + 
				"                         \"GlossSeeAlso\": \"GML\"\n" + 
				"                    },\n" + 
				"                    \"GlossSee\": \"markup\"\n" + 
				"                }\n" + 
				"            }\n" + 
				"        }\n" + 
				"    }\n" + 
				"}";
		String expectedOut = "{\n" + 
				" \"glossary.title\":\"example glossary\"\n" + 
				" \"glossary.GlossDiv.title\":\"S\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.ID\":\"SGML\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.SortAs\":\"SGML\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.GlossTerm\":\"Standard Generalized Markup Language\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.Acronym\":\"SGML\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.Abbrev\":\"ISO 8879:1986\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.GlossDef.para\":\"A meta-markup language, used to create markup languages such as DocBook.\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.GlossDef.GlossSeeAlso\":\"GML\"\n" + 
				" \"glossary.GlossDiv.GlossList.GlossEntry.GlossSee\":\"markup\"\n" + 
				"}";
		
		
		p.build(input);
		assertEquals(p.getOutput().toString(), expectedOut);
		
	}

}
