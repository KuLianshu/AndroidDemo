// IOnNewBookArrivedListener.aidl
package com.example.aidldemo;

import com.example.aidldemo.Book;

interface IOnNewBookArrivedListener {

    void onNewBookArrived(in Book newBook);

}