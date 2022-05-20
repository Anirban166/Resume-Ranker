<p align = "center">
<img src = "/Miscellaneous/Resume Ranker Logo.png" height = "300" width = "auto">
</p>  
<hr>

<h2 align = "center">
Functionality
</h2>

A Kotlin/Java application which scans the files from a specified directory (which as per intention, should contain CVs/resumes) for any number of specific keywords, reads the ones with `.doc`/`.pdf` extensions and subsequently enlists the keywords each file contains and the strength of it (given by the keyword count), in addition to telling apart the resume with the highest score. I built this early on with the mindset to help recruiters to rank resumes from a large candidate pool. Not the most ideal approach, but this is generally one of the steps in filtering out resumes by automated systems.

<hr>
<h2 align = "center">
I/O
</h2>

The program takes two inputs via a Swing-based (JFrame) graphical user interface: 
- A string of whitespace separated keywords.
- The directory (a string as well) in which the resumes are stored locally for the user.

When provided with these, it displays the total weightage of each resume and the best one among the lot with respect to the keyword-based search - all in a text box within the GUI. The results of a session are also saved in a separate file for future reference (sort of a log), and the location where it must be stored (along with the filename and extension) can be [configured](https://github.com/Anirban166/Resume-Ranker/blob/a4f7f3cc6a426ea024de483918eea6984891f04e/Source/Kotlin/main.kt#L2) easily within the program.

Here's a screenshot of a run with five legit resumes:
<p align = "center"> 
<img src = "/Output/Kotlin version/JFrame GUI screenshot from a single run with five resumes.png">
</p>   
  
And here's a short video to demonstrate multiple runs of the above case, leading to the same conclusion:
<p align = "center">
<img src = "/Output/Kotlin version/Demo.gif">
</p>   

<hr>
<h2 align = "center">
Notes
</h2>

Resumes other than the ones in the typical formats (i.e., docs and pdfs, including LaTeX ones) although not facilitated here, can be supported - one has to first mention the other extension(s) in the file filter [code segment](https://github.com/Anirban166/Resume-Ranker/blob/a4f7f3cc6a426ea024de483918eea6984891f04e/Source/Kotlin/ResumeScanner.kt#L129) (the easy part) and then proceed to write the corresponding 'specific-to-that-format'-handling text extractor block(s), using some well-defined library (unless you want to do that yourself). For instance, I used Apache POI and PDFBox libraries to handle .doc and .pdf file formats respectively.

<hr>
<h2 align = "center">
License
</h2>

<p align = "center">
Going with <a href = "https://github.com/Anirban166/Resume-Ranker/blob/master/Miscellaneous/License">Apache</a> for this one.
</p>  