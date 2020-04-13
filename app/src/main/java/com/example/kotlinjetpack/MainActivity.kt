package com.example.kotlinjetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), CharacterAdapter.EndReachedCallback {

    private lateinit var viewModel: CharacterViewModel
    private lateinit var recyclerView: RecyclerView

    private var adapter: CharacterAdapter = CharacterAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recylerview)

        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.characterLiveData.observe(this, Observer { list ->  adapter.setCharacterList(list)})
        viewModel.isEndLiveData.observe(this, Observer { adapter.setDoneLoading() })

        var linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun endReached() {
        viewModel.getCharacters()
    }
}
