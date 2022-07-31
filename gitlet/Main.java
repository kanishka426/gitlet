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
                if (!Repository.exists()) {
                    System.out.println("No Gitlet version-control system running in the current folder.");
                    return;
                }
                if(args.length != 2) {
                    System.out.println(String.format("Invalid number of arguments for command: add. Required 1 but got {}.", args.length - 1));
                    return;
                }
                Repository repository = Repository.loadRepository();
                repository.add(args[1]);
            }
                break;
            case "commit":{
                if (!Repository.exists()) {
                    System.out.println("No Gitlet version-control system running in the current folder.");
                    return;
                }
                if(args.length != 2) {
                    System.out.println("Please enter a valid commit message.");
                    return;
                }
                Repository repository = Repository.loadRepository();
                repository.commit(args[1]);

            }

            case "rm":{
                if (!Repository.exists()) {
                    System.out.println("No Gitlet version-control system running in the current folder.");
                    return;
                }
                if(args.length != 2) {
                    System.out.println(String.format("Invalid number of arguments for command: rm. Required 1 but got {}.", args.length - 1));
                    return;
                }
                Repository repository = Repository.loadRepository();
                repository.rm(args[1]);
            }

            case "log":{
                if (!Repository.exists()) {
                    System.out.println("No Gitlet version-control system running in the current folder.");
                    return;
                }
                if(args.length != 1) {
                    System.out.println(String.format("Invalid number of arguments for command: log. Required 0 but got {}.", args.length - 1));
                    return;
                }
                Repository repository = Repository.loadRepository();
                repository.log();
            }

            default:{
                System.out.println("No command with that name exists.");
            }
            break;
        }
    }
}
