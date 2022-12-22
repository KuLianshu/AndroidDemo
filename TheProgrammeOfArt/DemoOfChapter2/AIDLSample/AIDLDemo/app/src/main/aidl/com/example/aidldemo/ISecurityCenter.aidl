// ISecurityCenter.aidl
package com.example.aidldemo;


interface ISecurityCenter {

    String encrypt(String content);
    String decrypt(String password);

}
