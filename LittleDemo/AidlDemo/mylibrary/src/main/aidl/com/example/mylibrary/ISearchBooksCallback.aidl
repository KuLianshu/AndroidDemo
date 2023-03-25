// ISearchBooksCallback.aidl
package com.example.mylibrary;
import com.example.mylibrary.Book;

interface ISearchBooksCallback {
    void onSuccess(in List<Book> books);
    void onError(String msg);
}