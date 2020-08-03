// BookController.aidl
package com.example.server;

import com.example.server.Book;

interface BookController {

    List<Book> getBookList();

    void addBookInOut(inout Book book);

}
