package gitlet;

import java.io.File;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Kanishka Tiwari
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("Please enter a command.");
            return;
        }
        String firstArg = args[0];
        switch(firstArg) {
            case "init": {
                if(Repository.exists()){
                    System.out.println("A Gitlet version-control system already exists in the current directory.");
                    return;
                }
                Repository repository = new Repository();
                repository.setupPersistence();
            }
                break;
            case "add":{

            }
                // TODO: handle the `add [filename]` command
                break;
            default:{
                System.out.println("No command with that name exists.");
            }
            break;
        }
    }
}
