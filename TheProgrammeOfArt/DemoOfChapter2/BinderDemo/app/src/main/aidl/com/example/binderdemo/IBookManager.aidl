// IBookManager.aidl
package com.example.binderdemo;

import com.example.binderdemo.Book;
import java.util.List;

interface IBookManager {

    List<Book>getBookList();
    void addBook(in Book book);


}