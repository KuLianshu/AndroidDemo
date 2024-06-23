package com.hero.mvvmdemo.mvvm.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.hero.mvvmdemo.R;
import com.hero.mvvmdemo.databinding.ActivityMvvmpatternBinding;
import com.hero.mvvmdemo.mvvm.interfaces.OnClickPresenter;
import com.hero.mvvmdemo.mvvm.viewmodel.TranslateViewModel;

/**
 * View层
 */
public class MVVMActivity extends AppCompatActivity implements OnClickPresenter {

    private ActivityMvvmpatternBinding binding;
    private TranslateViewModel mvvmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvmpattern);
        initUIData();

        //点击事件的示例
        binding.setPresenter(this);

        //获取ViewModelProvider实例
        //获取ViewModel实例
        mvvmViewModel = new ViewModelProvider(this).get(TranslateViewModel.class);
        //AndroidViewModel使用此方法
        //        mvvmViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(TranslateViewModel.class);

        mvvmViewModel.getmTranslateData().observe(this, translateData -> {
            Toast.makeText(this, "标题：" + translateData.getTitle(), Toast.LENGTH_SHORT).show();
            binding.setTranslateBean(translateData);
        });

        mvvmViewModel.getLoadingLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //控制UI的逻辑在activity中，不要在xml中
                binding.progressPb.setVisibility(aBoolean != null && aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        binding.setMvvmViewModel(mvvmViewModel);
    }

    private void initUIData() {
        //不可改变的数据在这里处理，或者xml中写默认值
        //自定义BindingAdapter 的示例
        binding.setImageUrl("https://img.win3000.com/m00/d7/1a/d685398ab14eb827da7a883931cf38af.jpg");
        binding.setDefaultImageResource(R.drawable.whiteboard_blue);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.click_tv:
                mvvmViewModel.requestTranslateData();
                break;
        }
    }
}
