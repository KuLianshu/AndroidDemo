package com.example.flowpractice.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.flowpractice.R
import com.example.flowpractice.databinding.FragmentDownloadBinding
import com.example.flowpractice.databinding.FragmentHomeBinding
import com.example.flowpractice.download.DownloadManager
import com.example.flowpractice.download.DownloadStatus
import kotlinx.coroutines.flow.collect
import java.io.File


class DownloadFragment : Fragment() {

    val URL = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"

    private val mBinding: FragmentDownloadBinding by lazy {
        FragmentDownloadBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            context?.apply {
                val file = File(getExternalFilesDir(null)?.path,"pic.JPG")
                DownloadManager.download(URL,file).collect {status ->
                    when(status){
                        is DownloadStatus.Progress ->{
                            mBinding.apply {
                                progressBar.progress = status.values
                                tvProgress.text = "${status.values}%"
                            }
                        }
                        is DownloadStatus.Error ->{
                            Toast.makeText(context,"下载错误",Toast.LENGTH_SHORT).show()
                        }
                        is DownloadStatus.Done ->{
                            mBinding.apply {
                                progressBar.progress = 100
                                tvProgress.text = "100%"
                            }
                            Toast.makeText(context,"下载完成",Toast.LENGTH_SHORT).show()
                        }
                        else ->{
                            Log.i("WLY","下载失败...")
                        }
                    }

                }
            }
        }
    }



}