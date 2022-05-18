> To be updated soon.

<h2 align = "center">
Functionality
</h2>

Program which scans all files from any specified directory for specific keywords entered as input, reads the ones with `.doc`/`.pdf` extensions and subsequently returns the desired keywords found along with the total weightage of each resume. To be used as a keyword-based ranker for resumes by recruiters.

<h2 align = "center">
Notes
</h2>

Resumes other than the ones in the typical formats (docs and pdfs, including LaTeX ones) can be supported, albeit one has to first mention the extension(s) in the file filter code segment (easy part), and then proceed to write the corresponding specific-to-that-format handling text extractor block(s), using some well-defined library (unless you want to do that yourself). For instance, I used Apache POI and PDFBox libraries to handle docs and pdfs respectively.

<h2 align = "center">
Graphical User Interface
</h2>

- Java version <br>
Has a Jframe-based interface to take the whitespace separated keywords and the directory (both in separate lines) as inputs. The corresponding output is displayed in a textbox and written to a word document file as well, enacting as a log for the run. <br>