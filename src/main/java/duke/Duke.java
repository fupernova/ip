package duke;

import duke.command.ByeCommand;
import duke.command.Command;

import java.io.*;

public class Duke {


    private static final String FILEPATH = "tasklogs/tasks.txt";

    private Storage storage;
    private Ui ui;
    private TaskList taskList;
    private Parser parser;
    private Object ByeCommand;

    public Duke() {
        storage = new Storage(FILEPATH);
        ui = new Ui();
        taskList = new TaskList(ui);
        parser = new Parser(taskList, ui);
        try {
            storage.loadFile(taskList.getTasks());
        } catch (IOException e) {
            System.out.println("IO error.");
        }
    }
    public void run() {
        try {
            ui.printGreeting();
            while(true) {
                String command = ui.getCommand();
                Command parsedCommand = parser.parseCommands(command);
                if (parsedCommand instanceof ByeCommand) {
                    break;
                }
                parsedCommand.execute(ui);
            }
            ui.sayGoodbye();
            storage.saveFile(taskList.getTasks());
        } catch (IOException e) {
            System.out.println("IO error.");
        }
    }

    public static void main(String[] args) {
        new Duke().run();
    }
}
