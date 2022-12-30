// IBookManager.aidl
package com.example.aidldemo;

import com.example.aidldemo.Book;

interface IBookManager {
    List<Book> getBookList();
        void addBook(in Book book);
}