# Resume-Keyword-Scanner
Hackathon entry for a Java program which reads resumes and desired keywords, returns keywords found and the total weightage of resume. (Can be used as a resume ranker among a list of resumes) - Files include .pdf/.doc formats. (Executable+GUI might be added later.)

```
Files to be uploaded on 15/09/19. within UTC:2-10:00.
```

Support for DOC/PDF:
---
Added via Apache POI and PDFBox (jar) libraries with all dependencies clear by the latest build version. 

Issues:
---
(1) POI HPWF's extractor with fileData (String array) as object has access to reading the file by characters, and no other methods that support reading words. <br>
Status: Solved by extracting contents into another file in local workspace and reading from that. (Bingo)

(2) PDF Document Entry function gives log4j warnings. <br>
Status: Solved by using a Basic configurator for that function dec. line only. (Warning free console - Beri nice)

(3) PDF Encryption rule-pass check. <br>
Status: Added Encryption check and imported TextStrippers based on area. (Brainwave)

(4) Buffreader has only LineReader method hence all scanning is line-based which returns multiple instances of keyword-findings and weightage. <br>
Status: Temporary Solution - write in a line with no carriage return or similar line breaking (eof) characters. (like enter) <br>
>> Update : All string read methods in doc follow line reading and hence it is difficult to resolve this. (tried with streamers, buffers, files-class etc. but same result) Follow temporary solution for now. (for doc files).
>> Update : Solved by splitting directly from filedata.

(5) PDF/DOC Recognition in a File Directory. <br>
Status: Solved by using FileFilters.

(6) Files iteration / Reading all files in a directory: <br>
Status: Solved by implemented a for each loop iterating through the files in a directory with only pdf/doc listing as specified in Filechooser class and then differentiating from those two based on their ending name.

(7) File Redundancy on both blocks due to previous I/O methods. <br>
Status: Solved by introducing f-parameters on each block.

(8)
 
