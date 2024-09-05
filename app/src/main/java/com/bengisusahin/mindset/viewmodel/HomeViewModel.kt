package com.bengisusahin.mindset.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _isEgoChecked = MutableLiveData<Boolean>().apply { value = true }
    val isEgoChecked: LiveData<Boolean> get() = _isEgoChecked

    private val _addedItemsSet = MutableLiveData<MutableSet<Int>>(mutableSetOf())
    val addedItemsSet: LiveData<MutableSet<Int>> get() = _addedItemsSet

    fun setEgoChecked(checked: Boolean) {
        _isEgoChecked.value = checked
        if (checked) {
            _addedItemsSet.value?.clear()
        }
    }

    fun addItem(itemId: Int) {
        val currentSet = _addedItemsSet.value ?: mutableSetOf()
        currentSet.add(itemId)
        _addedItemsSet.value = currentSet
    }

    fun removeItem(itemId: Int) {
        val currentSet = _addedItemsSet.value ?: mutableSetOf()
        currentSet.remove(itemId)
        _addedItemsSet.value = currentSet
    }
}
