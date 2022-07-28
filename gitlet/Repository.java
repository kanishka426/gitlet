package gitlet;

import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  does at a high level.
 *
 *  This class represents the repository in which Gitlet is initialised. This class provides the CWD, and a
 *  File GITLET_DIR representing the .gitlet folder.
 *  This class also contains the Commit tree of the folder.
 *
 *  @author Kanishka Tiwari
 */
public class Repository implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");

    /** The Commit Tree of the repository */

    public final CommitTree COMMIT_TREE = new CommitTree();

    /** The Staging Area of the repository */

    public final StagingArea STAGING_AREA = new StagingArea();

    /** Log of the repository */

    public final Log LOG = new Log(COMMIT_TREE);


    /** Check for if Gitlet is initialised */
    public static boolean exists(){
        return GITLET_DIR.exists();
    }

    /** Setting up files and folders after the command: gitlet init */
    public void setupPersistence(){
        GITLET_DIR.mkdir();
        File fileCopies = join(GITLET_DIR, "fileCopies");
        fileCopies.mkdir();
        File repository = join(GITLET_DIR, "repository.bin");
        try{
            repository.createNewFile();
        } catch(IOException e){
            System.out.println("I/O exception error occured while setting up persistence.");
        }
    }

    public void remove(String fileName){
        File file = join(CWD, fileName);
        if(file.exists()){
            file.delete();
        }

    }

    public File getFile(String fileName){
        File file = join(CWD, fileName);
        if(file.exists()){
            return file;
        }else{
            return null;
        }
    }

    public void saveFileCopies(Map<String, String> references){

        for(Map.Entry<String, String> entry: references.entrySet()){
            File fileFolder = join(GITLET_DIR, "fileCopies", entry.getKey());
            File fileCopy = join(fileFolder, entry.getValue());
            File fileCurrent = join(CWD, entry.getKey());
            writeContents(fileCopy, readContents(fileCurrent));
        }
        return ;
    }

    public void save(){
        File repo = join(GITLET_DIR, "repository.bin");
        writeContents(repo, this);
    }


}
