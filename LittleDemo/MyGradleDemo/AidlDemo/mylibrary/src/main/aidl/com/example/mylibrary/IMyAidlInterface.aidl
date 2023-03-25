// IMyAidlInterface.aidl
package com.example.mylibrary;
import com.example.mylibrary.Book;
import com.example.mylibrary.ISearchBooksCallback;

interface IMyAidlInterface {

    int borrowBook(String bookName);
    int returnBook(String bookName);

    int borrowOtherBook(in Book book);

    List<Book> getBookList();

    oneway void getBooks(ISearchBooksCallback callback);
}
