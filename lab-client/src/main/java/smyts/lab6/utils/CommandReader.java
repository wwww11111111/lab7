package smyts.lab6.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

public class CommandReader {

    private Scanner scanner;
    private final Stack<DataReader> readers = new Stack<>();
    private final HashSet<String> usedScripts = new HashSet<>();
    private DataReader currentReader;
    boolean exeStatus;

    public void init() {
        this.scanner = new Scanner(System.in);
        exeStatus = false;
    }

    public String getCommand() {
        String command;
        do {
            command = getNextLine();
        } while ((command.trim().equals("")));
        return command;
    }

    public String getNextLine() {
        boolean commandReady = false;
        String commandToReturn = null;

        while (!commandReady) {
            String command = getString();
            if (command.trim().equals("")) {
                return "";
            }
            if (command.split(" ")[0].equalsIgnoreCase("execute_script")) {
                if (command.split(" ").length == 2) {
                    if (usedScripts.contains(command.split(" ")[1])) {
                        System.out.println("обнаружена рекурсия!");
                        exeStatus = false;
                        usedScripts.clear();
                        readers.clear();
                        continue;
                    }
                    try {
                        currentReader = readers.push(new DataReader(command.split(" ")[1]));
                        usedScripts.add(command.split(" ")[1]);
                        exeStatus = true;
                    } catch (UnsupportedEncodingException | FileNotFoundException e) {
                        System.out.println("Ошибка доступа к файлу!");
                        exeStatus = false;
                        usedScripts.clear();
                        readers.clear();
                    }
                } else System.out.println("Команда execute_script должна содержать имя исполняемого файла!");
            } else {
                commandReady = true;
                commandToReturn = command;
            }
        }
        return commandToReturn;
    }

    private String getString() {
        if (!exeStatus) {

            return scanner.nextLine();
        }
        return currentReader.readline();
    }
}
