// BookController.aidl
package com.example.server;

import com.example.server.Book;
import com.example.server.IOnNewBookArrivedListener;

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);

}
