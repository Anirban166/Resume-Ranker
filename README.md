# Keyword-Based Resume Ranker
Java program which scans all files from any specified directory and reads the ones with .doc and .pdf extensions, extracting desired keywords found and returning the total weightage of each resume. (To be used as a keyword-based ranker for resumes)
```
Files uploaded on 15/09/19 within UTC:02-10:00
```
<img src="Runscreens/Resumescannerconsole+gui.png" width="100%">
<img src="Runscreens/runscreen_gui_doc.png" width="100%">

Support for DOC/PDF:
---
Added via Apache POI and PDFBox libraries.

Issues Solved: 
---
(1) Basics like keyword input and support for keyword prefixes. <br>
>> Took a null string (for dynamic size) wherein I took input from a scanner object and then applied the ReadLine() method to that string breaking the string into words by using split(“ “) method (distinguishes words by whitespace character) and storing those keywords in a string array. To include keywords ending at periods, commas etc I included such character exceptions.

(2) Apache POI HPWF's extractor has access to reading the file by ParagraphText method, and no other methods that support reading words. <br>
>> Solved by extracting contents into another file in local workspace and reading from that.

(2) PDF Document extractor method gives log4j warnings. <br>
>> Solved by using a Basic configurator inside main method. (Warning free console)

(3) PDF Encryption rule-pass check. <br>
>> Added Encryption check and used TextStrippers based on area.

(4) Buffer reader has only LineReader method hence all scanning is line-based which returns multiple instances of keyword-findings (by each line) and weightage. <br>
>> Solved by splitting file content directly from filedata into a character stream.

(5) PDF/DOC Recognition in a File Directory. <br>
>> Solved by using FileFilters.

(6) Files iteration / Reading all files in a directory: <br>
>> Solved by implementing a for each loop iterating through the files in a directory with only pdf/doc listing as specified in Filechooser class and then differentiating from those two based on their ending name.


Extras
---
(1) Added application interface (Jframe+Swinger GUI) to take keywords and directory as input and display output in a textbox. <br>
(2) Output will be displayed on three platforms - the eclipse console, the GUI’s output textbox and in a word file as well (log). <br>
(3) Added support for Latex.
 
