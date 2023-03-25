package com.example.databindingdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.databindingdemo.databinding.ActivityMain2Binding;
import com.example.databindingdemo.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.databindingdemo.BR.user;

public class Main2Activity extends Activity {

    private int index = 0;

    private ItemAdapter mItemAdapter;
    private ArrayList<User> mItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMain2Binding binding = DataBindingUtil.setContentView(this,R.layout.activity_main2);

        mItems = new ArrayList<>();
        for (int i =0; i<2;i++){
            User user = new User();
            user.setId(index+"");
            user.setName("User"+index);
            user.setBlog("www.csdn.net/zhangsan@"+index);
            mItems.add(user);
            index++;
        }

        binding.rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItemAdapter = new ItemAdapter();
        binding.rv.setAdapter(mItemAdapter);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setId(index+"");
                user.setName("User"+index);
                user.setBlog("www.csdn.net/zhangsan@"+index);
                mItems.add(user);
                mItemAdapter.notifyDataSetChanged();
                index++;
            }
        });

    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder>{

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                    R.layout.recycle_view_item,viewGroup,false);
            return new ItemViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int i) {
            viewHolder.getBinding().setVariable(user,mItems.get(i));
            viewHolder.getBinding().executePendingBindings();
        }

        @Override
        public int getItemCount() {

            return mItems.size();
        }
    }



    private class ItemViewHolder extends RecyclerView.ViewHolder{
        private ViewDataBinding binding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public void setBinding(ViewDataBinding binding) {
            this.binding = binding;
        }
    }

}
