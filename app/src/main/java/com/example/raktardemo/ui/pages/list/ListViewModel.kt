package com.example.raktardemo.ui.pages.list

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listPresenter: ListPresenter
) : RainbowCakeViewModel<ListViewState>(Loading) {

    /*fun setList() = execute {
        val items = listPresenter.getItems()

        private val _posts = MutableLiveData<List<Post>>()
        val posts: LiveData<List<Post>> = _posts

        viewState = ListContent(
            list = items,
            isLoading = false
        )
    }*/

    fun setList() = execute  {
        val storages = listPresenter.getStorages()

        listPresenter.getItems().collect {
            viewState = ListContent(
                list = it,
                storages = storages,
                isLoading = false
            )
        }
    }

    /*fun setList() = execute {
        viewState = ListContent(list = databaseInteractor.getItems(), isLoading = false)
    }*/
}
