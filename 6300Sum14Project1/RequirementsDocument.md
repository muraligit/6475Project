# Requirements Document – Team 22

## 1 User Requirements

##### 1.1 Software Interfaces 

**External Systems**

The external system the software will interact with primarily is the File System (to read user provided files).

**Libraries**

We will be using the standard Java 1.7 Libraries along with the JUnit 4 Framework for testing.

##### 1.2 User Interfaces 

The software will interact with users via the command line. Users will need to be able to open up a command prompt on their computers and be able to type in the needed parameters (the path to their essay text file, along with optional parameters) on their keyboards.

The software will output the overall average number of words in their sentences (or a helpful error message if an issue occurs). 

##### 1.3 User Characteristics 

- Users will be University Students.
- Users will be using a variety of computers and operating systems (Windows, Linux/Unix, Mac).
- Users will have a varied amount of technical expertise / computer experience (ranging from No Experience - Proficient)

## 2 System Requirements 

These subsections contain all the software requirements at a level of detail sufficient to enable 
designers/developers to design/develop a system that satisfies those requirements, and testers to test 
that the system satisfies those requirements. This part of the document should provide a numbered 
(possibly hierarchical) list of simple, complete, and consistent functional and non-functional 
requirements. 

##### 2.1 Functional Requirements 

**The software must:**
1. Be executable from the command line
2. Allow the user to specify a file path to their text file as a command line argument (this is a required argument)
3. Allow the user to specify delimiters with the -d short flag, followed by an integer (this is an optional argument, defaulting to .?!)
	1. Per the instructor notes, the comma should not be used as a default delimiter.	
4. Allow the user to specify the lower word length limit with the -l short flag, followed by an integer (this is an optional argument, defaulting to 3)
5. Output the Average Sentence Length (defined as the average number of valid words across all sentences) rounded down to the nearest integer.
	1. Helpful messages should be returned to the user in the case of an error.
6. Contain the following classes:
	1. A class called "Main" that implements the command line interface for the program.
	2. A class called "AverageSentenceLength" that implements the main program functionality.
7. Pass All Unit Tests
	


##### 2.2 Non-Functional Requirements 
 
 **The software must:**
 1. Be Documented (manual.txt)
 2. Be Able to Process a reasonably sized essay (1 MB or less) within 1 second.
 3. Handle errors gracefully