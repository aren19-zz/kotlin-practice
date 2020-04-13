package com.example.kotlinjetpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinjetpack.data.Character
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterViewModel : ViewModel() {

    private var repository: CharacterRepository = Module.getCharacterStore()
    private var disposable: CompositeDisposable = CompositeDisposable()
    private var page: Int = 1
    private var isEnd:Boolean = false
    private var charList:MutableList<Character> = mutableListOf()

    private val characterMutableLiveData: MutableLiveData<List<Character>> = MutableLiveData()
    private val isEndMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()

    val characterLiveData: LiveData<List<Character>> = characterMutableLiveData
    val isEndLiveData: LiveData<Boolean> = isEndMutableLiveData

    fun getCharacters() {
        if (!isEnd) {
            disposable.add(repository
                .getCharacters(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.first) {
                        isEnd = true
                        isEndMutableLiveData.value = response.first
                    } else {
                        page++
                    }
                    charList.addAll(response.second)
                    characterMutableLiveData.value = charList
                })
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}