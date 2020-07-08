package com.example.com.presentation.ui.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.com.data.utils.doBackgroundObserveMain

abstract class PaginatedFragment<VM : PaginatedViewModel, BINDING : ViewDataBinding> : BaseFragment<VM, BINDING>(),
    SwipeRefreshLayout.OnRefreshListener {

    abstract val adapter: BaseAdapter

    abstract val layoutManager: LinearLayoutManager

    abstract val emptyStub: View

    abstract val swipeRefresh: SwipeRefreshLayout

    abstract val recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.nextPage()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.setOnRefreshListener(this)
        recyclerView.let {
            it.layoutManager = layoutManager
            it.adapter = adapter
            it.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val lastItem = layoutManager.findLastVisibleItemPosition()
                    val itemsCount: Int = layoutManager.itemCount
                    if (lastItem >= itemsCount - 3) viewModel.nextPage()
                }
            })
        }
    }

    override fun onDestroyView() {
        recyclerView.apply {
            layoutManager = null
            adapter = null
        }
        super.onDestroyView()
    }

    override fun onRefresh() {
        viewModel.refresh()
    }

    override fun subscribeOnViewModel() {
        super.subscribeOnViewModel()
        with(disposable) {
            add(viewModel.isRefresh
                .doBackgroundObserveMain()
                .subscribe {
                    if (!it) layoutManager.scrollToPosition(0)
                    swipeRefresh.isRefreshing = it
                })
            add(viewModel.isEmpty
                .doBackgroundObserveMain()
                .subscribe { if (it) showEmpty() else hideEmpty() })
            add(viewModel.data
                .doBackgroundObserveMain()
                .subscribe { adapter.items = ArrayList(it) })
        }
    }

    private fun showEmpty() {
        swipeRefresh.visibility = View.GONE
        emptyStub.visibility = View.VISIBLE
    }

    private fun hideEmpty() {
        emptyStub.visibility = View.GONE
        swipeRefresh.visibility = View.VISIBLE
    }
}