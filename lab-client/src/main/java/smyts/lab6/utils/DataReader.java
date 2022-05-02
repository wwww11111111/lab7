package smyts.lab6.utils;


import smyts.lab6.exceptions.NoSomeArgumentsException;

import java.io.*;
import java.util.Scanner;

/**
 * class for reading the information from the file and from terminal
 */

public class DataReader {
    //class responsible for parcing csv file by lines and checking its correctness
    private String path;
    private FileInputStream fis = null;
    private InputStreamReader isr = null;
    private Scanner scanner = new Scanner(System.in);

    private BufferedReader br = null;

    public DataReader(String path) throws UnsupportedEncodingException, FileNotFoundException {
        this.path = path;


        fis = new FileInputStream(path);
        isr = new InputStreamReader(fis, "UTF-8");
        br = new BufferedReader(isr);

    }

    /**
     * @return string from user
     */

    public String readline() {
        try {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }

}