package com.example.myapplication.okhttp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载多个文件
 */
public class DownloadFilesDemo {

    public void test(){
        OkHttpClient client = new OkHttpClient();
        List<String> imageUrls = Arrays.asList("http://example.com/image1.jpg", "http://example.com/image2.jpg");
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (String url : imageUrls) {
            executorService.submit(() -> {
                Request request = new Request.Builder().url(url).build();
                try (Response response = client.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        // 处理下载的图片数据
//                        Files.copy(response.body().byteStream(), Paths.get("downloaded_image.jpg"), StandardCopyOption.REPLACE_EXISTING);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        // 关闭线程池
        executorService.shutdown();
    }

}
