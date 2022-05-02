package smyts.lab6.utils;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Scanner;

public class UserInputHandler {

    boolean exeStatus = false;
    Scanner scanner = new Scanner(System.in);
    DataReader reader = null;
    HashSet<String> scripts = new HashSet<>();

    public String getCommand() {

        if (exeStatus) {
            String command = reader.readline();
            if (command.split(" ").length == 0) {
                return getCommand();
            }
            if (scriptProcessing(command)) return getCommand();

            if (command == null) {
                exeStatus = false;
                return scanner.nextLine();
            }
            return command;
        }

        String command = scanner.nextLine();
        if (command.split(" ").length == 0) {
            return getCommand();
        }

        if (scriptProcessing(command)) return getCommand();
        return command;

    }

    private boolean scriptProcessing(String command) {
        if (command.split(" ")[0].equals("execute_script")) {
            if (command.split(" ").length == 2 && scripts.add(command.split(" ")[1])) {
                try {
                    reader = new DataReader(command.split(" ")[1]);
                    exeStatus = true;
                    return true;
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    System.out.println("ошибка, проверьте правильность указанного пути до файла.");
                    return true;
                }
            }

            if (command.split(" ").length == 1) {
                System.out.println("execute_script должна содержать в качестве аргумента путь до скрипта.");
                return true;
            }

            if (!scripts.add(command.split(" ")[1])) {
                System.out.println("скрипты вызывают сами себя!");
                exeStatus = false;
                scripts.clear();
                return true;
            }
        }
        return false;
    }
}
