package com.example.myapplication.okhttp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 下载单个文件
 */
public class DownloadFileDemo {
    private OkHttpClient client;

    public DownloadFileDemo() {
        client = new OkHttpClient();
    }

    public void downloadFileAsync(String fileUrl, String destinationPath) {
        Request request = new Request.Builder()
                .url(fileUrl)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("下载失败: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("服务器返回错误: " + response.code());
                }

                ResponseBody body = response.body();
                if (body == null) {
                    throw new IOException("响应体为空");
                }

                File outputFile = new File(destinationPath);
                try (InputStream inputStream = body.byteStream();
                     FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    System.out.println("文件下载成功，保存到: " + destinationPath);
                } catch (IOException e) {
                    System.err.println("文件写入失败: " + e.getMessage());
                }
            }
        });
    }

    public void main(String[] args) {

        String fileUrl = "https://example.com/path/to/file.zip";
        String destinationPath = "path/to/downloaded/file.zip";
        downloadFileAsync(fileUrl, destinationPath);
    }
}
