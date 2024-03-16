package com.example.flowpractice.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.flowpractice.adapter.ArticleAdapter
import com.example.flowpractice.databinding.FragmentArticleBinding
import com.example.flowpractice.viewmodel.ArticleViewModel
import com.example.flowpractice.viewmodel.UserViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect


class ArticleFragment : Fragment() {

    private val viewModel by viewModels<ArticleViewModel>()

    private val mBinding: FragmentArticleBinding by lazy {
        FragmentArticleBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return mBinding.root
    }

    private fun TextView.textWatcherFlow(): Flow<String> = callbackFlow {
        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {}
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launchWhenCreated {
            mBinding.etSearch.textWatcherFlow().collect {
                Log.i("WLY","collect keywords $it")
                viewModel.searchArticles(it)
            }
        }
        context?.let {
            val adapter = ArticleAdapter(it)
            mBinding.recyclerView.adapter = adapter
            viewModel.articles.observe(viewLifecycleOwner,{articles->
                adapter.setData(articles)
            })
        }
    }

}