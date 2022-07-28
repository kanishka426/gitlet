package gitlet;

public class Log {

    private CommitTree commitTree;

    public Log(CommitTree commitTree){
        this.commitTree = commitTree;
    }

    public void log(){
        StringBuilder str = new StringBuilder();
        for(Commit node: commitTree){
            str.append(node.toString());
        }
        System.out.println(str.toString());
    }

    public void globalLog(){

    }

}
