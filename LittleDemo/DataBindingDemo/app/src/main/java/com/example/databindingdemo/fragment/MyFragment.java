package com.example.databindingdemo.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.databindingdemo.R;
import com.example.databindingdemo.databinding.FragmentMyBinding;

public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentMyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my,container,false);

        binding.text.setText(R.string.hello_world);
        return binding.getRoot();
    }
}
