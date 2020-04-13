package com.example.kotlinjetpack

import com.example.kotlinjetpack.data.Character
import com.example.kotlinjetpack.network.NetworkService
import io.reactivex.Observable

class CharacterRepository(private val networkService: NetworkService) {

    fun getCharacters(page:Int): Observable<Pair<Boolean, List<Character>>> {
        return networkService.characterApi.getCharacters(page).map { response ->
            var isEnd = false
            if (response.info.next == null) {
                isEnd = true
            }
            Pair(isEnd, response.results)
        }
    }

}