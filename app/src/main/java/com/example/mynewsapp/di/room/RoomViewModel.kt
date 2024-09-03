package com.example.mynewsapp.di.room

import androidx.lifecycle.ViewModel
import com.example.mynewsapp.datasource.db.Folder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val repository: RoomRepository
):ViewModel(){
    val folders: StateFlow<List<Folder>> = repository.folders

}