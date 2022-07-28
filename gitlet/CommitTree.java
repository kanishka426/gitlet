package gitlet;

import java.io.Serializable;
import java.time.Month;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.time.LocalDateTime;

public class CommitTree implements Serializable {
    private Commit HEAD;
    private String HEAD_BRANCH_NAME;
    private HashMap<String, Commit> branches = new HashMap();
    private Commit sentinel;
    public CommitTree(){
        branches.put(
                "master",
                new Commit("initial commit",null)
        );
        this.HEAD = branches.get("master");
        this.HEAD_BRANCH_NAME = "master";
        this.sentinel = HEAD;
    }
    public void commit(String message, Map<String, String> fileReferences) {
        Commit commit = new Commit(message, HEAD);
        HEAD = commit;
        for (Map.Entry<String, String> entry : fileReferences.entrySet()) {
            HEAD.addReference(entry.getKey(), entry.getValue());
        }
        branches.replace(HEAD_BRANCH_NAME, HEAD);
    }

    public Map<String, String> getReferences(){
        return HEAD.getReferences();
    }






}
