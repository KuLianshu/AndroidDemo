package com.example.chapter7;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LayoutAnimationDemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_animation_demo);
        ListView listView = findViewById(R.id.list_view);
        List<String> list = new ArrayList<>();
        for (int i=0; i<15; i++){
            list.add(i+"");
        }
        MyAdapter myAdapter = new MyAdapter(this,list);
        listView.setAdapter(myAdapter);
//        //除了可以在xml中设置动画，也可以用代码设置
//        Animation animation = AnimationUtils.loadAnimation(this,R.anim.anim_item);
//        LayoutAnimationController controller = new LayoutAnimationController(animation);
//        controller.setDelay(0.5f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        listView.setLayoutAnimation(controller);
    }

    class MyAdapter extends BaseAdapter {

        private List<String> list;
        private Context context;

        public MyAdapter(Context context, List<String> list){
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            if (list!=null){
                return list.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            if (list!=null){
                return list.get(i);
            }
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyHolder myHolder;
            if (view==null){
                view = LayoutInflater.from(context).inflate(R.layout.list_item_layout,null);
                myHolder = new MyHolder();
                view.setTag(myHolder);
            }else {
                myHolder = (MyHolder) view.getTag();
            }

            myHolder.textView = view.findViewById(R.id.text_view);
            myHolder.imageView = view.findViewById(R.id.image);

            myHolder.textView.setText(list.get(i));
            if (i%3==0){
                myHolder.imageView.setImageResource(R.mipmap.cat);
            }else if (i%3==1){
                myHolder.imageView.setImageResource(R.mipmap.cat1);
            }else {
                myHolder.imageView.setImageResource(R.mipmap.cat2);
            }

            return view;
        }

        class MyHolder{
            TextView textView;
            ImageView imageView;
        }


    }
}
