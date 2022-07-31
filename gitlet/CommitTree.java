package gitlet;



import java.util.*;

public class CommitTree implements Iterable<Commit> {
    private String HEAD;
    private String HEAD_BRANCH_NAME;
    private HashMap<String, String> branches = new HashMap();
    private String sentinel;

    public CommitTree(Repository repository){
        Commit c = new Commit("initial commit",null, new TreeMap<>());
        branches.put(
                "master", c.getID()
        );
        this.HEAD = branches.get("master");
        this.HEAD_BRANCH_NAME = "master";
        this.sentinel = HEAD;
        repository.saveCommit(sentinel, c);
    }

    public void commit(Repository repository, String message, Map<String, String> fileReferences) {
        Commit commit = new Commit(message, HEAD, fileReferences);
        HEAD = commit.getID();
        branches.replace(HEAD_BRANCH_NAME, HEAD);
        repository.saveCommit(HEAD, commit);

        repository.save();
    }


    public Map<String, String> getReferences(Repository repository) {
        Commit commit = repository.getCommit(HEAD);
        return commit.getReferences();
    }

    @Override
    public Iterator<Commit> iterator(){
        return new TreeIterator();
    }

    public Iterator<Commit> iterator(String branchName){
        return new TreeIterator(branchName);
    }


    private class TreeIterator implements Iterator<Commit>{

        private String curr;

        public TreeIterator(){
            curr = HEAD;
        }

        public TreeIterator(String branchName){
            this.curr = branches.get(branchName);
        }

        @Override
        public boolean hasNext(){
            if(curr.equals(sentinel)){
                return false;
            }
            return true;
        }

        @Override
        public Commit next(){
            Commit returnValue = Repository.getCommit(curr);
            curr = returnValue.getParent();
            return returnValue;
        }
    }




}
