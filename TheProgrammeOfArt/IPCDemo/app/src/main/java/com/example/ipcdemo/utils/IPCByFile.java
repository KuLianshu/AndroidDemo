package com.example.ipcdemo.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.ipcdemo.entity.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class IPCByFile {

    public static void persistToFile(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = new User(2,"Hello World !",false);
                File dir = new File(MyConstants.CHAPTER_2_PATH);

                Log.i("WLY","user = "+user.toString()+", dir = "+dir);

                if (!dir.exists()){
                    boolean result = dir.mkdirs();
                    Log.i("WLY","create File result = "+result);
                }else {
                    Log.i("WLY","file is exit!");
                }


                File cachedFile = new File(MyConstants.CHANGE_FILE_PATH);
                ObjectOutputStream objectOutputStream = null;
                try {
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(cachedFile));
                    objectOutputStream.writeObject(user);

                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if (objectOutputStream!=null){
                            objectOutputStream.close();
                            objectOutputStream = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    public static void recoverFromFile(){
        Log.i("WLY","-----2");

        new Thread(new Runnable() {
            @Override
            public void run() {
                User user = null;
                File cachedFile = new File(MyConstants.CHANGE_FILE_PATH);
                if (cachedFile.exists()){
                    ObjectInputStream objectInputStream =null;
                    try {
                        objectInputStream = new ObjectInputStream(new FileInputStream(cachedFile));
                        user = (User) objectInputStream.readObject();
                        if (user!=null){
                            Log.i("WLY","user = "+user.toString());
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        if (objectInputStream!=null){
                            try {
                                objectInputStream.close();
                                objectInputStream = null;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }


            }
        }).start();
    }

}
