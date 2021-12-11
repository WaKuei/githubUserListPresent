package com.wakuei.githubuserlistpresent.network

import com.wakuei.githubuserlistpresent.model.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiExecutor {
    companion object {
        fun getUsers(id: Int, uiCallback: UiCallback.UsersCallback) {
            val call: Call<ArrayList<UserModel>> =
                RetrofitInterface.getInStance().getUsers(id, Config.itemDefaultCount)
            val cb: Callback<ArrayList<UserModel>> = object : Callback<ArrayList<UserModel>> {
                override fun onResponse(
                    call: Call<ArrayList<UserModel>?>,
                    response: Response<ArrayList<UserModel>?>
                ) {
                    when (response.code()) {
                        200 -> {
                            if (response.body() != null)
                                uiCallback.onGetUsersSuccess(response.body())
                        }
                        304 -> {
                            uiCallback.onError("304 Not Modified")
                        }
                        403 -> {
                            uiCallback.onError("403 API rate limit exceeded")
                        }
                        else -> {
                            uiCallback.onError("API Error")
                        }
                    }
                }

                override fun onFailure(call: Call<ArrayList<UserModel>?>, t: Throwable) {
                    uiCallback.onError("Network Error")
                }
            }
            call.enqueue(cb)
        }
    }
}