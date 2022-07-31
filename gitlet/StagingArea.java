package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;

public class StagingArea {

    private Map<String, String> addedReferences = new TreeMap<>();

    private Map<String, Object> addedFiles = new TreeMap<>();
    private Set<String> removedFiles = new HashSet<>();


    private void clear(){
        addedReferences.clear();
        removedFiles.clear();
        addedFiles.clear();
    }

    public void commit(Repository repository, CommitTree commitTree, String message){
        Map<String, String> references = new TreeMap<>();
        references.putAll(commitTree.getReferences(repository));
        for(String fileToRemove: removedFiles){
            references.remove(fileToRemove);
        }
        for(Map.Entry<String, String> entry: addedReferences.entrySet()){
            references.put(entry.getKey(), entry.getValue());
        }

        commitTree.commit(repository, message, references);
        repository.saveFileCopies(addedReferences, addedFiles);

        clear();
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

        if(commitTree.getReferences(repository).containsKey(fileName)) { /**Checks if the file exists in the previous commit. */
            currentSHA1 = commitTree.getReferences(repository).get(fileName);
        }

        if(currentSHA1 == null || !fileSHA1.equals(currentSHA1)) {
            addedReferences.put(fileName, fileSHA1);
            addedFiles.put(fileName, Utils.readContents(file));
        }

        repository.save();

        System.exit(0);
    }

    public void rm(Repository repository, CommitTree commitTree, String fileName){
        Boolean printError = true;
        if(addedReferences.containsKey(fileName)){
            addedReferences.remove(fileName);
            printError = false;
        }
        if(commitTree.getReferences(repository).containsKey(fileName)) {
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
