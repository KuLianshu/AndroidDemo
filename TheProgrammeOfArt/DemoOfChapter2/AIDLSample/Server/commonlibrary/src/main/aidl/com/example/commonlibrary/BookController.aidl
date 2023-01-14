// BookController.aidl
package com.example.commonlibrary;

import com.example.commonlibrary.Book;
import com.example.commonlibrary.IOnNewBookArrivedListener;

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

    void registerListener(IOnNewBookArrivedListener listener);

    void unregisterListener(IOnNewBookArrivedListener listener);

}
