package com.example.myapplication.retrofit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提交多个文件
 */
public class SubmitFilesDemo {

    public void test(List<String> imagePaths){
        //步骤2.创建OKHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 设置连接超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                // 设置读取超时时间
                .readTimeout(15, TimeUnit.SECONDS)
                // 设置写入超时时间
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        //步骤3.创建Retrofit实例
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("服务器地址")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //步骤4.创建MultipartBody.Part对象列表
        List<MultipartBody.Part> images = new ArrayList<>();
        for (String imagePath: imagePaths){
            File file = new File(imagePath);
            //创建RequestBody对象，用于包装文件
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
            //创建MultipartBody.Part对象
            MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", file.getName(),requestBody);
            //添加要上传的图片到image列表中
            images.add(imagePart);
        }

        //步骤5.执行上传请求
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> call = apiService.uploadImages(images);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    //处理上传成功的响应
                }else {
                    //处理上传失败的响应
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //处理上传失败的响应
            }
        });
    }
}
