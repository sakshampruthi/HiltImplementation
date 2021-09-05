package com.saksham.hiltimplementation.ui

import androidx.lifecycle.*
import com.saksham.hiltimplementation.model.Blog
import com.saksham.hiltimplementation.repository.MainRepository
import com.saksham.hiltimplementation.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetBlogsEvent -> {
                    mainRepository.getBlog()
                        .onEach {
                            _dataState.value = it
                        }.launchIn(viewModelScope)
                }
                is MainStateEvent.None ->{

                }
            }
        }
    }

}

sealed class MainStateEvent {
    object GetBlogsEvent : MainStateEvent()

    object None : MainStateEvent()
}