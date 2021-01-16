

# Coding Challenge

Interview is a Java 8 maven project. It takes a json input file and
outputs flattened key(separated by a "dot")/value pairs.

Main source code: src->main->Program.java

Junit source code: src->test->ProgramTest.java

## Usage

`java -jar ./Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar <json file>`

## Compilation

`mvn clean install`

Above generates a ./target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar. Please use the jar with dependencies.

## Test Runs


- Running with Example input json

```
~/Downloads $ cat json.txt
{
    "a": 1,
    "b": true,
    "c": {
        "d": 3,
        "e": "test"
    }
}
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./json.txt
{
 "a":1
 "b":true
 "c.d":3
 "c.e":"test"
}
```

- Running with a more complex json input

```
~/Downloads $ cat json2.txt
{
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

~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./json2.txt
{
 "glossary.title":"example glossary"
 "glossary.GlossDiv.title":"S"
 "glossary.GlossDiv.GlossList.GlossEntry.ID":"SGML"
 "glossary.GlossDiv.GlossList.GlossEntry.SortAs":"SGML"
 "glossary.GlossDiv.GlossList.GlossEntry.GlossTerm":"Standard Generalized Markup Language"
 "glossary.GlossDiv.GlossList.GlossEntry.Acronym":"SGML"
 "glossary.GlossDiv.GlossList.GlossEntry.Abbrev":"ISO 8879:1986"
 "glossary.GlossDiv.GlossList.GlossEntry.GlossDef.para":"A meta-markup language, used to create markup languages such as DocBook."
 "glossary.GlossDiv.GlossList.GlossEntry.GlossDef.GlossSeeAlso":"GML"
 "glossary.GlossDiv.GlossList.GlossEntry.GlossSee":"markup"
}

```
- Running with Example input json and float values this time

```
~/Downloads $ cat json3.txt
{
    "a": 1.0,
    "b": true,
    "c": {
        "d": 3.345,
        "e": "test"
    }
}
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./json3.txt
{
 "a":1.0
 "b":true
 "c.d":3.345
 "c.e":"test"
}

```
- Running with empty input json

```
~/Downloads $ cat emptyjson.txt 
{}
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./emptyjson.txt 
{
}

```
- Running with no input (empty string aka 0 byte input json file).

```
~/Downloads $ ls -l noinput.txt 
-rw-r--r--  1 rakeshsharma  staff  0 Jan 16 10:51 noinput.txt
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./noinput.txt 
{
}

```
- Running with malformed json input. Output is JSONParser Exception.

```
~/Downloads $ cat malformedjson.txt 
{
    "a": 
    "b": true,
    "c": {
        "d": 3,
        "e": "test"
    }
}
```

The value in key "a" is missing in above.

```
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./malformedjson.txt 
Exception in thread "main" Unexpected token COLON(:) at position 17.
	at org.json.simple.parser.JSONParser.parse(Unknown Source)
	at org.json.simple.parser.JSONParser.parse(Unknown Source)
	at main.Program.build(Program.java:110)
	at main.Program.main(Program.java:80)

```
- Running with incorrect input file path. Program gives FileNotFoundException.

```
~/Downloads $ ls -l filedoesnotexist.txt
ls: filedoesnotexist.txt: No such file or directory
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./filedoesnotexist.txt
Exception in thread "main" java.io.FileNotFoundException: ./filedoesnotexist.txt (No such file or directory)
	at java.io.FileInputStream.open0(Native Method)
	at java.io.FileInputStream.open(FileInputStream.java:195)
	at java.io.FileInputStream.<init>(FileInputStream.java:138)
	at java.io.FileReader.<init>(FileReader.java:72)
	at main.Program.main(Program.java:71)
```
- Running with no arguments. Program prints Usage.

```
~/Downloads $ java -jar /Users/rakeshsharma/eclipse-workspace/Interview/target/Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
Usage:-
java -jar ./Interview-0.0.1-SNAPSHOT-jar-with-dependencies.jar <json file>
```
## Development/IDEs

- *Eclipse* - Eclipse IDE for Java Developers. Version: 2019-03 (4.11.0). Build id: 20190314-1200
- *Java* - Java 8


## Running Tests

The standard test suite can be run with the command:

`mvn test`


## Questions

Please email me at srakesh@hotmail.com. Thank you.

