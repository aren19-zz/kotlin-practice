package com.example.kotlinjetpack.network

import com.example.kotlinjetpack.data.CharacterResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApi {

    @GET("character/")
    fun getCharacters(@Query("page") page: Int) : Observable<CharacterResponse>
}