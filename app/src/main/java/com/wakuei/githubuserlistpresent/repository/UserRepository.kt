package com.wakuei.githubuserlistpresent.repository

import android.text.TextUtils
import com.wakuei.githubuserlistpresent.model.UserModel
import com.wakuei.githubuserlistpresent.network.ApiExecutor
import com.wakuei.githubuserlistpresent.network.UiCallback

class UserRepository {

    fun loadUserData(id: Int, callback: OnUserApiCallback) {
        callback.startLoading()
        ApiExecutor.getUsers(id, object : UiCallback.UsersCallback {
            override fun onGetUsersSuccess(items: ArrayList<UserModel>?) {
                if (items != null) callback.getData(items)
                callback.finishLoading()
            }

            override fun onError(errorMessage: String?) {
                if (!TextUtils.isEmpty(errorMessage))
                    callback.errorMessage(errorMessage!!)
                callback.finishLoading()
            }
        })
    }

    interface OnUserApiCallback {
        fun startLoading()
        fun finishLoading()
        fun getData(data: ArrayList<UserModel>)
        fun errorMessage(errorMsg: String)
    }
}