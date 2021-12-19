package com.wakuei.githubuserlistpresent.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wakuei.githubuserlistpresent.databinding.ActivityMainBinding
import com.wakuei.githubuserlistpresent.repository.UserRepository
import com.wakuei.githubuserlistpresent.viewmodel.UserViewModel
import com.wakuei.githubuserlistpresent.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var mBinding: ActivityMainBinding
    private var mAdapter: UserAdapter? = null
    private var mRepository = UserRepository()
    private var mViewModelFactory = ViewModelFactory { UserViewModel(mRepository) }
    private var mViewModel:UserViewModel?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
        mViewModel?.getUsers()
    }

    private fun init() {
        mViewModel = ViewModelProvider(this, mViewModelFactory).get(UserViewModel::class.java)

        mAdapter = UserAdapter(this)
        mBinding.rvList.adapter = mAdapter
        mBinding.rvList.layoutManager = LinearLayoutManager(this)
        mBinding.rvList.addOnScrollListener(OnListScrollListener())

        mBinding.sfData.setOnRefreshListener(this)

        mViewModel?.mList?.observe(this, {
            mAdapter?.submitList(it)
        })

        mViewModel?.mIsLoading?.observe(this, {
            if (it) {
                mBinding.pbLoading.visibility = View.VISIBLE
            } else {
                mBinding.pbLoading.visibility = View.GONE
                mBinding.sfData.isRefreshing = false
            }
        })

        mViewModel?.mErrorMessage?.observe(this, {
            if (!TextUtils.isEmpty(it)) Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onRefresh() {
        mViewModel?.getUsers()
    }

    inner class OnListScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val linearLayoutManager = mBinding.rvList.layoutManager as LinearLayoutManager
            if (mBinding.pbLoading.visibility == View.GONE) {
                if (mAdapter!=null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mAdapter!!.itemCount - 1) {
                    mViewModel?.getMoreUsers()
                }
            }
        }
    }
}