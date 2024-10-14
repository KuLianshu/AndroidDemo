package com.example.demoofchapter11;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorDemo {

    //加载图片
    public void test0() {
        // 创建一个固定大小的线程池，线程数量为 CPU 核心数
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        // 异步加载图片
        executor.submit(() -> {
            // 加载图片代码
            // ...
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    //处理数据库操作
    public void test1() {
        // 创建一个单线程的线程池
        ExecutorService executor = Executors.newSingleThreadExecutor();
        // 异步处理数据库操作
        executor.submit(() -> {
            // 数据库操作代码
            // ...
        });
    }

    //处理网络请求
    public void test2() {
        // 创建一个固定大小的线程池，线程数量为 3
        ExecutorService executor = Executors.newFixedThreadPool(3);
        // 异步处理网络请求
        executor.submit(() -> {
            // 网络请求代码
            // ...
        });
    }

    //处理后台任务
    public void test3() {
        // 创建一个固定大小的线程池，线程数量为 5
        ExecutorService executor = Executors.newFixedThreadPool(5);
        // 异步处理后台任务
        executor.submit(() -> {
            // 后台任务代码
            // ...
        });
    }

    public void test4() {
        //创建一个可重用固定线程数的线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //创建实现了Runnable接口对象
        Thread tt1 = new MyThread();
        Thread tt2 = new MyThread();
        Thread tt3 = new MyThread();
        Thread tt4 = new MyThread();
        Thread tt5 = new MyThread();
        //将线程放入池中并执行
        pool.execute(tt1);
        pool.execute(tt2);
        pool.execute(tt3);
        pool.execute(tt4);
        pool.execute(tt5);
        //关闭
        pool.shutdown();
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " is running.");
        }
    }


}