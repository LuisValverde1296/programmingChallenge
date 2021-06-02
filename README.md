# Programming Challenge #

This challenge consists on creating a Groovy script to search and replace a certain pattern of text within all files starting in a given directory

## Required ##

	- Groovy
	- Java 8 or superior

## Instruccions to run the script ##
	
	1. Start a new command line at the folder that contains the script.
	2. Type a command with the following format:
	   - groovy .\script.groovy pathToDirectory "pattern that will be replaced" "text to replace the pattern" [OPTIONAL]pathForOutputFile
	   - Example with 3 args: groovy .\script.groovy C:\Users\Admin\directory "pattern that will be replaced" "text to replace the pattern"
	   - Example with 4 args: groovy .\script.groovy C:\Users\Admin\directory "pattern that will be replaced" "text to replace the pattern" C:\Users\Admin\directory\output.txt
	