package com.example.flowpractice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.flowpractice.R
import com.example.flowpractice.databinding.FragmentNumberBinding
import com.example.flowpractice.databinding.FragmentSharedBinding
import com.example.flowpractice.model.SharedFlowViewModel
import com.example.flowpractice.viewmodel.NumberViewModel

class SharedFragment : Fragment() {
    private val viewModel by viewModels<SharedFlowViewModel>()

    private val mBinding: FragmentSharedBinding by lazy {
        FragmentSharedBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mBinding.apply {
            btnStart.setOnClickListener {
                viewModel.startRefresh()
            }

            btnStop.setOnClickListener {
                viewModel.stopRefresh()
            }
        }
    }

}