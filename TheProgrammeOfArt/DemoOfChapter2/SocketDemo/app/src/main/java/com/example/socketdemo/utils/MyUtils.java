package com.example.socketdemo.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class MyUtils {

    public static void close(BufferedReader in){
        if (in!=null){
            try {
                in.close();
                in = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(PrintWriter out){
        if (out!=null){
            out.close();
            out = null;
        }
    }

}
