# CvExtractor
Extract a candidade information from CV

___Note: You need at least Java Runtime Environment 1.8___

## Build instructiosn ##
The easiest way to build the project into one jar file is to use Eclipse IDE.

First of all clone the repository:

```git clone https://github.com/smyskoff/CvExtractor.git CvExtractor```

Then open Eclipse and select _File -> Import..._ In _General_ category select _"Existing Projects into Workspace"_ and push _Next_. Select the root directory---recently cloned repository, check checkbox near CvExtractor project in the list and push _Finish_.

Now you see CvExtractor project in the Package Explorer tree.

In Package Explorer tree find ```CvExtractor.java``` file (```src/default/CvExtractor.java```), and open it in the editor (just doublecklick on it). Then just press ```Ctrl+F11``` (or _Run -> Run_ in the menu) just to create run configuration for this file.

Next, right click on it, select _Export..._, in _Java_ category choose _"Runnable JAR file"_ and push _Next_.
* in _"Launch configuration"_ drop-down box select recently created "CvExtractor - CvExtractor" item;
* in _"Export destination"_ choose where to save the output JAR file;
* in _"Library handling"_ select _"Package required libraries into generated JAR"_.

That's it, just push _Finish_.

## Usage ##
For the moment you've got the output file, say ```cvextractor.jar```.

The program itself just tries to extract candidate name, phone and e-mail from CV, and print it in the form:

```
User name: Jack Black
Phone: +1 (234) 567 8901
E-mails: j.black@heavy-metal.com
```

And a bit of testing information.

To run the application open terminal, whichever you prefer (Windows users can use ```cmd``` or ```PowerShell```), change directory to where the output JAR file is:

```
cd path/to/jar/file
```

or, for Windows users:

```
cd Disk:\path\to\jar\file
```

So, now you can run the application:

```
java -jar cvextractor.jar [file1.pdf [file2.pdf [...]]]
```

Paths to files could be relative or absolute.
