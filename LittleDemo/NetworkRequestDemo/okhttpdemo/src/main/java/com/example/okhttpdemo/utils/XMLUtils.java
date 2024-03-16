package com.example.okhttpdemo.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.okhttpdemo.entity.BrandBean;
import com.example.okhttpdemo.entity.Nics;
import com.example.okhttpdemo.entity.User;
import com.example.okhttpdemo.entity.UserList;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XMLUtils {

    /**
     * 02:解析设备类型:typeid
     */
    public void XmlToBean(Context context) {

        String fileName = "xml_test1.xml";   //assets目录下的文件名
        InputStream in = null;
        try {
            in = context.getResources().getAssets().open(fileName);
            XStream xStream = new XStream(new DomDriver());//创建Xstram对象
            xStream.autodetectAnnotations(true);
            xStream.processAnnotations(Nics.class);
            xStream.ignoreUnknownElements();
            Nics mNics = (Nics) xStream.fromXML(in);
            Log.i("WLY",mNics.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("WLY",e.getMessage());
        }

    }

    /**
     * 02:解析设备类型:typeid
     */
    public void XmlToBean1(Context context) {

        String fileName = "xml_test.xml";   //assets目录下的文件名
        List<BrandBean.Item> myList = new ArrayList<>();
        InputStream in = null;

        try {
            in = context.getResources().getAssets().open(fileName);
            XStream xStream = new XStream();
            xStream.processAnnotations(BrandBean.class);
            BrandBean result = (BrandBean) xStream.fromXML(in);
            Log.i("WLY",result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("WLY",e.toString());

        }

    }



    /**
     * Bean转XML并保存为xml文件
     */
    public void BeanToXml() {

        UserList userList=new UserList();
        List<User> users=new ArrayList<>();
        User li = new User("25", "li");
        User liu = new User("26", "liu");
        users.add(li);
        users.add(liu);
        userList.setUserList(users);
        XStream xStream = new XStream();
        xStream.alias("Document",UserList.class);   //改类成员名
        xStream.alias("Item",User.class);         //改类成员名
        xStream.aliasField("List",UserList.class,"mUserList"); //改类成员属性名
        String s = xStream.toXML(userList);
        Log.i("WLY",s.toString());


//        File saveFile = new File(Environment.getExternalStorageDirectory(), "测试目录文件.xml");
//        FileOutputStream outStream = null;
//        try {
//            outStream = new FileOutputStream(saveFile);
//            outStream.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>".getBytes());
//            outStream.write("\n".getBytes());
//            outStream.write(s.getBytes());
//
//            outStream.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//
//
//        }


    }



}
