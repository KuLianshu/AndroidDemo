package com.example.flowpractice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowpractice.R
import com.example.flowpractice.databinding.FragmentArticleBinding
import com.example.flowpractice.databinding.FragmentNumberBinding
import com.example.flowpractice.viewmodel.ArticleViewModel
import com.example.flowpractice.viewmodel.NumberViewModel
import kotlinx.coroutines.flow.collect


class NumberFragment : Fragment() {

    private val viewModel by viewModels<NumberViewModel>()


    private val mBinding: FragmentNumberBinding by lazy {
        FragmentNumberBinding.inflate(layoutInflater)
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
            btnPlus.setOnClickListener {
                viewModel.increment()
            }
            btnMinus.setOnClickListener {
                viewModel.decrement()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.number.collect {value ->
                mBinding.tvNumber.text = "$value"
            }
        }

    }

}