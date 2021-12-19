package com.wakuei.githubuserlistpresent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wakuei.githubuserlistpresent.repository.UserRepository
import com.wakuei.githubuserlistpresent.model.UserModel
import timber.log.Timber

class UserViewModel(private val userRepository: UserRepository) : ViewModel() {

    val mList = MutableLiveData<List<UserModel>>()
    val mIsLoading = MutableLiveData<Boolean>()
    val mErrorMessage = MutableLiveData<String>()
    private var mHasMore = true
    private val mUserRepositoryCallback = OnUserRepositoryCallback()
    private var mDataList = ArrayList<UserModel>()

    fun getUsers() {
        Timber.d("Reload Users Data! ")
        mHasMore = true
        mDataList = ArrayList()
        userRepository.loadUserData(0, mUserRepositoryCallback)
    }

    fun getMoreUsers() {
        Timber.d("get more Users Data , hasMore:$mHasMore")
        if (mHasMore) {
            var lastId = 0
            if (mDataList.size > 0) lastId = mDataList.last().id
            userRepository.loadUserData(lastId, mUserRepositoryCallback)
        }
    }

    inner class OnUserRepositoryCallback : UserRepository.OnUserApiCallback {
        override fun startLoading() {
            mIsLoading.postValue(true)
        }

        override fun finishLoading() {
            mIsLoading.postValue(false)
        }

        override fun getData(data: ArrayList<UserModel>) {
            if (data.size == 0)
                mHasMore = false
            mDataList.addAll(data)
            mList.postValue(mDataList)
        }

        override fun errorMessage(errorMsg: String) {
            mErrorMessage.postValue(errorMsg)
        }
    }
}