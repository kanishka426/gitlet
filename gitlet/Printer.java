package gitlet;

public class Printer {


    public void log(CommitTree commitTree){
        StringBuilder str = new StringBuilder();
        for(Commit node: commitTree){
            str.append(node.toString());
        }
        System.out.println(str.toString());
    }

    public void globalLog(){

    }

    public void status(Repository repository, CommitTree commitTree, StagingArea stagingArea){


    }

}
