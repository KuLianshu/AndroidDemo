package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.databindingdemo.databinding.ActivityMain13Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main13Activity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMain13Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main13);

        String[] array={"zhangsan","lisi"};
        binding.setArray(array);

        List<String> list = new ArrayList<>();
        list.add("11");
        list.add("22");
        binding.setList(list);

        Map<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","24");
        binding.setMap(map);

        Set<String> set = new HashSet<>();
        set.add("11");
        set.add("22");
        binding.setSet(set);
//        binding.setSparse();

        binding.setIndex(0);
        binding.setKey("name");

    }
}
