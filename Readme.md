<p align = "center">
<img src = "/Miscellaneous/Revamped Logo.png" height = "200" width = "auto">
</p>  
<hr>

<h2 align = "center">
Functionality
</h2>

A multiplatform Kotlin application which scans the files from a specified directory (which as per intention, should contain CVs/resumes) for any number of specific keywords, reads the ones with `.doc`/`.pdf` extensions and subsequently enlists the keywords each file contains and the strength of it (given by the keyword count), in addition to telling apart the resume with the highest score. I built this early on with the mindset to help recruiters to rank resumes from a large candidate pool. Not the most ideal approach, but this is generally one of the steps in filtering out resumes by automated systems.

<hr>
<h2 align = "center">
I/O
</h2>

The program takes three inputs via a Compose-based graphical user interface: 
- A string of whitespace separated keywords.
- The directory in which the resumes are stored locally for the user.
- The location (along with the file specifics, i.e. with its name and extension) where a separate file containing the results for the session will be saved.

When provided with these, it displays the total weightage of each resume and the best one among the lot with respect to the keyword-based search - all in a text box within the flexible GUI, and additionally within the user-specified file that contains the entire trace of the run for future reference (sort of enacting as a log).  

<hr>
<h2 align = "center">
Notes
</h2>

Resumes other than the ones in the typical formats (i.e., docs and pdfs, including LaTeX ones) although not facilitated here, can be supported - one has to first mention the other extension(s) in the file filter code segment (the easy part) and then proceed to write the corresponding 'specific-to-that-format'-handling text extractor block(s), using some well-defined library (unless you want to do that yourself). For instance, I used Apache POI and PDFBox libraries to handle .doc and .pdf file formats respectively.

<hr>
<h2 align = "center">
License
</h2>

<p align = "center">
Going with <a href = "https://github.com/Anirban166/Resume-Ranker/blob/master/Miscellaneous/License">Apache</a> for this one.
</p>  
