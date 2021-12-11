package com.wakuei.githubuserlistpresent.network

import com.wakuei.githubuserlistpresent.model.UserModel

class UiCallback {
    interface UsersCallback {
        fun onGetUsersSuccess(items: ArrayList<UserModel>?)
        fun onError(errorMessage: String?)
    }
}