package com.wakuei.githubuserlistpresent

import com.wakuei.githubuserlistpresent.repository.UserRepository
import com.wakuei.githubuserlistpresent.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {
    viewModel { UserViewModel(get()) }
}

val repoModule = module {
    single { UserRepository() }
}