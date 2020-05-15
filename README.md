# Keyword-Based Resume Ranker
Java program which scans all files from any specified directory for specific keywords entered as input, reads the ones with `.doc` and `.pdf` extensions and subsequently returns the desired keywords found along with the total weightage of each resume. To be used as a keyword-based ranker for resumes.

---
<img src="Runscreens/Resumescannerconsole+gui.png" width="100%">
<img src="Runscreens/runscreen_gui_doc.png" width="100%">

Support for DOC/PDF
---
Added via Apache POI and PDFBox libraries.

Issues Solved 
---
- Basics like keyword input and support for keyword prefixes: <br>
Took a null string (for dynamic size) with input from a scanner object and then applied the `ReadLine()` method to that string, breaking it into words by using the `split(“ “)` method (distinguishing words by whitespace) and storing the same in a string array. To include keywords ending at periods, commas etcetra, I included prefixed and suffixed character exceptions.

- Apache POI HPWF's extractor has access to reading the file by ParagraphText method, and no other methods that support reading words: <br>
Solved by extracting contents into another file in local workspace and reading from the same.

- PDF Document extractor method gives log4j warnings: <br>
Solved by using a Basic configurator inside the main method. (Warning free console)

- PDF Encryption rule-pass check. <br>
Added Encryption check and used TextStrippers based on area.

- Buffer reader has only LineReader method hence all scanning is line-based which returns multiple instances of keyword-findings (by each line) and weightage. <br>
Solved by splitting file content directly from filedata into a character stream.

- PDF/DOC Recognition in a File Directory. <br>
Solved by using FileFilters. Note: A for each loop iterates through the files in a directory, listing only .pdf/.doc files as specified in Filechooser class. (The differentiation between them is achieved by comparing them by their ending name) For other file formats to be supported, the extensions must be added to the FileFilter, with corresponding text-extractor blocks.

Output
---
- Added Jframe Application interface to take keywords and directory as input, and correspondingly display output in a textbox. <br>
- Resultant output can be found on the GUI’s output textbox plus in a (log) word document file as well. <br>
 
