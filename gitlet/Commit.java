package gitlet;

// TODO: any imports you need here

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author Kanishka Tiwari
 */
public class Commit {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Commit parent;
    private Date dateTime;

    private boolean isMerge = false;
    private Map<String, String> fileReferences;

    private String id;
    public Commit(String message, Commit parent, Map<String, String> fileReferences) {
        this.message = message;
        this.parent = parent;
        this.dateTime = new Date();
        this.fileReferences = fileReferences;
        this.id = Utils.sha1(message, parent, dateTime.toString(), fileReferences.toString());
    }


    /** GETTERS ---------------------------------------------- */
    public void addReference(String fileName, String id){
        fileReferences.put(fileName, id);
    }

    public Map<String, String> getReferences(){ return fileReferences; }

    public Date getDateTime(){ return dateTime; }

    public String message(){ return message; }

    public Commit getParent(){ return this.parent; }

    public String getID(){
        return this.id;
    }

    /** ------------------------------------------------------ */

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append("===");
        str.append("commit " + this.id + "\n");
        SimpleDateFormat format = new SimpleDateFormat("EE MMM d HH:mm:ss yyyy Z");
        str.append("Date: " + format.format(dateTime));
        str.append('\n');
        if(this.isMerge){
            /** TO DO */
        }
        str.append(message);
        if(message.charAt(message.length() - 1) != '\n'){
            str.append('\n');
        }

        return str.toString();
    }

}
