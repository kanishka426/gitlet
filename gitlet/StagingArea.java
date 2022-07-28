package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class StagingArea {

    private Map<String, String> addedFiles = new TreeMap<>();
    private Set<String> removedFiles = new HashSet<>();


    public void commit(Repository repository, CommitTree commitTree, String message){
        Map<String, String> references = commitTree.getReferences();
        for(String fileToRemove: removedFiles){
            references.remove(fileToRemove);
        }
        for(Map.Entry<String, String> entry: addedFiles.entrySet()){
            references.put(entry.getKey(), entry.getValue());
        }

        commitTree.commit(message, references);
        repository.saveFileCopies(references);

        repository.save();
    }

    public void add(Repository repository, CommitTree commitTree, String fileName) {

        File file = repository.getFile(fileName);

        if(!file.exists()){
            System.out.println("File does not exist.");
            System.exit(0);
        }

        String fileSHA1 = Utils.sha1(Utils.readContents(file));
        String currentSHA1 = null; // Current SHA1 of filename.

        if(commitTree.getReferences().containsKey(fileName)) { /**Checks if the file exists in the previous commit. */
            currentSHA1 = commitTree.getReferences().get(fileName);
        }

        if(currentSHA1 == null || !fileSHA1.equals(currentSHA1)) {
            addedFiles.put(fileName, fileSHA1);
        }

        repository.save();

        System.exit(0);
    }

    public void rm(Repository repository, CommitTree commitTree, String fileName){
        Boolean printError = true;
        if(addedFiles.containsKey(fileName)){
            addedFiles.remove(fileName);
            printError = false;
        }
        if(commitTree.getReferences().containsKey(fileName)) {
            removedFiles.add(fileName);
            repository.remove(fileName);
            printError = false;
        }
        if(printError){
            System.out.println("No reason to remove the file.");
        }

        repository.save();
    }


}
