// IBookManager.aidl
package com.example.aidldemo;

import com.example.aidldemo.Book;
import com.example.aidldemo.IOnNewBookArrivedListener;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);

}