// IBookManager.aidl
package com.example.binderdemo;

import com.example.binderdemo.Book;
import java.util.List;

interface IBookManager {

    List<Book>getBookList();
    //in 表示输入型参数，out表示输出型参数，inout表示输入输出型参数
    void addBook(in Book book);


}