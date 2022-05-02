package smyts.lab6.utils;


import smyts.lab6.common.util.Request;
import smyts.lab6.common.util.UserData;

import java.util.Scanner;

public class Authorizator {

    private Scanner scanner = new Scanner(System.in);


    public Request makeRequest() {
        boolean registering = askRegisterOrLogin();
        Request request = new Request();
        request.setCommandNameAndArguments("authorization " +
                (registering ? "r" : "l"));
        request.setUserData(UserData.getFromConsole());
        return request;
    }

    private boolean askRegisterOrLogin() {
        System.out.println("Вы хотите зарегистрироваться или войти? [r/l]");
        String answer = scanner.nextLine();
        while (!answer.equals("r".trim()) && !answer.equals("l".trim())) {
            System.out.println("Выберите один вариант ответа!");
            answer = scanner.nextLine();
        }
        return answer.equals("r");
    }
}
