TEXT SEARCH ENGINE PROGRAM

This is an application which takes a directory and read text files under the directory and put all words into memory.
Then when you want to search a sentence inside of the directory, It searches the memory and tries to find best matches.
It returns usage percentage of the words that you searched with the file name information.

For Example: Assume that there are two files: text1.txt and text2.txt
text inside of the text1.txt file : to be or not to be
text inside of the text2.txt file : welcome to the world.

when you search "to be or not to be", It returns that result:

text1.txt=100%
text2.txt=16%


--------- How it works? -------------

When it read text1.txt it put each word with their count inside of a map: Map<fileName, Map<wordName, wordCount>>
For Example: 
<text1.txt, <to,2>, <be,2>, <or,1>, <not,1>
<text2.txt, <welcome,1>, <to,1>, <the,1>, <world,1>

when you search "to be or not to be" : It searches each word inside of each file Map and calculate usage of each word.
Total count of the seareched word is 6 points and text1.txt = 6 points too beause it contains all the words.
But text.txt= 1 point because it just contains "to".

Then it calculates rank. text1.txt = 100% because it contains 6 to 6.
text2.txt = 16% because it contains 1 to 6.


-------- How to run the application -----------

1) with intelliJ : 
- import the maven project
- from maven menu click clean and then install options
- wait until see the build success message.
- when build success it will create a jar file under of the target folder in the project.  Normally the file name is "program-0.0.1-SNAPSHOT.jar"
- open terminal in the intelliJ and go to the target folder 
	For example: cd /Desktop/program/target
- run the jar file on the terminal:
	For Example: java -jar program-0.0.1-SNAPSHOT.jar /Users/yasemin.akturk/Desktop/test/tests

program-0.0.1-SNAPSHOT.jar -> name of the jar file that was created after install
/Users/yasemin.akturk/Desktop/test/tests. -> file path that contains text files which will be read.

2) except intelliJ:
- there is a jar file in the program folder(textEngine.jar) that I have already created. copy it into your computer
- open terminal in your computer and go to the folder which contains the jar file
- run below comment: 
	java -jar textEngine.jar /Users/yasemin.akturk/Desktop/test/tests

textEngine.jar -> name of the jar file that you copied
/Users/yasemin.akturk/Desktop/test/tests -> file path that contains text files which will be read.



