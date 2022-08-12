package com.example.raktardemo.ui.pages.list.release

import co.zsmb.rainbowcake.base.RainbowCakeViewModel
import com.example.raktardemo.data.model.Release
import com.example.raktardemo.data.model.StoredItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReleaseViewModel @Inject constructor(
    private val releasePresenter: ReleasePresenter
) : RainbowCakeViewModel<ReleaseViewState>(Loading) {

    fun setRelease(item: StoredItem) {
        viewState = ReleaseContent(item, false)
    }

    fun setReleaseGroup(group: List<StoredItem>, acqId: String?) {
        viewState = ReleaseGroupContent(group, acqId, false)
    }

    fun onRelease(release: Release, item: StoredItem?, acqId: String?, group: List<StoredItem>) = execute {
        releasePresenter.onRelease(release, item, acqId, group)
    }
}
