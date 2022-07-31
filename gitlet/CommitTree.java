package gitlet;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.io.Serializable;
import java.time.Month;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.time.LocalDateTime;

public class CommitTree implements Iterable<Commit> {
    private Commit HEAD;
    private String HEAD_BRANCH_NAME;
    private HashMap<String, Commit> branches = new HashMap();
    private Commit sentinel;
    public CommitTree(){
        branches.put(
                "master",
                new Commit("initial commit",null, new TreeMap<>())
        );
        this.HEAD = branches.get("master");
        this.HEAD_BRANCH_NAME = "master";
        this.sentinel = HEAD;
    }
    public void commit(String message, Map<String, String> fileReferences) {
        Commit commit = new Commit(message, HEAD, fileReferences);
        HEAD = commit;
        branches.replace(HEAD_BRANCH_NAME, HEAD);
    }


    public Map<String, String> getReferences(){
        return HEAD.getReferences();
    }

    @Override
    public Iterator<Commit> iterator(){
        return new TreeIterator();
    }

    public Iterator<Commit> iterator(String branchName){
        return new TreeIterator(branchName);
    }

    private class TreeIterator implements Iterator<Commit>{

        private Commit curr;

        public TreeIterator(){
            curr = HEAD;
        }

        public TreeIterator(String branchName){
            this.curr = branches.get(branchName);
        }

        @Override
        public boolean hasNext(){
            if(curr == sentinel){
                return false;
            }
            return true;
        }

        @Override
        public Commit next(){
            Commit returnValue = curr;
            curr = curr.getParent();
            return returnValue;
        }
    }




}
