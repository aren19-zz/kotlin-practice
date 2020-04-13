package com.example.kotlinjetpack

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

    var characterLiveData: MutableLiveData<List<Character>> = MutableLiveData()
    var isEndLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getCharacters() {
        if (!isEnd) {
            disposable.add(repository
                .getCharacters(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.first) {
                        isEnd = true
                        isEndLiveData.value = response.first
                    } else {
                        page++
                    }
                    charList.addAll(response.second)
                    characterLiveData.value = charList
                })
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}