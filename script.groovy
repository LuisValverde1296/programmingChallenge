/*
 * Author: Luis Antonio Valverde Cruz
 * Version: 01/06/2021
 * Title: Programming Challenge for Accenture's interview.
 */

//TextReplacer class
/*Args: 
 *[0]: Path to a directory which contains text files.
 *[1]: Original text or pattern to use for searching in the files.
 *[2]: String that will replace the text pattern if found in the file.
 *[3]: Optional argument to a file for outputting a list of which files were modified.
 */
class TextReplacer{

    //Method to recollect the name of the files contained in the specified directory.
    //EFE: if the folder's path exists, add the path to the files to a list and returns that list.
    //otherwise, notifies the error and stops the program.
    def extractFilesFromFolder(folderPath){
        def filesInFolder = []
        try {
            //The eachFileRecurse navigates through the directories in the given path and iterate over them excecuting the instructions inside the brackets.
            new File(folderPath).eachFileRecurse {
                files -> filesInFolder << files.getAbsolutePath();
            }
            // println filesInFolder;
            return filesInFolder;
        } catch(e){
            println "failed to open the folder ${folderPath}";
            System.exit(1);
        }
    }

    //EFE: creates a backup of the original file into a new file and modifies the original one.
    //also takes the outputFile and writes the path of the modified files in it.
    //Args: 
    //filePath: path to the file we're modifying.
    //textPattern: string or pattern we're gonna replace in the file.
    //replaceWith: string we're using to replace the textPattern.
    //outputFile: the path to the output file.
    //useOutputFile: Bool that tells the program if the user gave an outputFile's path
    def replaceTextInFile(filePath, textPattern, replaceWith, outputFile, useOutputFile){
        if(!(new File(filePath).isDirectory())){
            def dotPosition = filePath.indexOf('.');
            def backupPath = filePath-(filePath.substring(dotPosition))+("backUp" + filePath.substring(dotPosition));
            
            def ant = new groovy.ant.AntBuilder();

            File myFile = new File(filePath);
            def originalDate = myFile.lastModified();
            File backupFile = new File(backupPath);
            backupFile.text = myFile.text;
            
            //This is where magic happens, this function replaces all the tokens for the choosen value into the specific file.
            //This function also has a variant where it modifies all the files into the specified directory but I didn't choose this option
            //Because I wanted to be able to show which where the modified files.
            ant.replace(file: filePath, token:textPattern, value:replaceWith);
            def modifiedDate = myFile.lastModified();
            
            if(useOutputFile && originalDate!=modifiedDate){
                outputFile.withWriterAppend {w ->
                    w << filePath + System.getProperty("line.separator");
                }
            }
        } else {

        }
    }

    //Main method of the class
    //EFE: controls the program's workflow.
    static void main(args) {
        def folderPath; //Arg [0]
        String textPattern; //Arg [1]
        String replaceWith; // Arg [2]
        def outputFileName; // Arg[3]
        def useOutputFile = false; //Bool that tells the program if the optional argument was given
        def myTextReplacer = new TextReplacer(); //Class instance
        
        Date initTime = new Date();
        println "starting time: " + initTime.format("hh:mm:ss");

        //Switch to verifiy the ammount of arguments given.
        switch(args.length){
            case 3:
                folderPath = args[0];
                textPattern = args[1];
                replaceWith = args[2];
                useOutputFile = false;
                break;
            case 4:
                folderPath = args[0];
                textPattern = args[1];
                replaceWith = args[2];
                outputFileName = args[3];
                useOutputFile = true;
                break;
            default:
                println "wrong ammount of arguments";
                System.exit(2);
        }

        def filesInFolder = myTextReplacer.extractFilesFromFolder(folderPath);
        
        File outputFile;
        if(useOutputFile){
            try{
                outputFile = new File(outputFileName);
                outputFile.withWriterAppend {w ->
                    w << "Modified Files:" + System.getProperty("line.separator");
                }
            } catch(e){
                println "couldn't open the output file at " + outputFileName ;
                System.exit(3);
            }
        }

        for(filePath in filesInFolder){
            myTextReplacer.replaceTextInFile(filePath, textPattern, replaceWith, outputFile, useOutputFile);
        }
        Date finishTime = new Date();
        println "finishing time: " + finishTime.format("hh:mm:ss");
        def duration = finishTime.getTime() - initTime.getTime();
        println "Program duration: ${duration} milliseconds";
    }
}