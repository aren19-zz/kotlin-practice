package com.example.kotlinjetpack

import com.example.kotlinjetpack.network.NetworkService

object Module {

    private val characterRepository: CharacterRepository

    init {
        val networkService =  NetworkService()
         characterRepository = CharacterRepository(networkService)

    }

    fun getCharacterStore(): CharacterRepository {
        return characterRepository
    }
}