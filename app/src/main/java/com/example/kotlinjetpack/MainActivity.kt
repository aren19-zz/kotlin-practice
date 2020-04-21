package com.example.kotlinjetpack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class MainActivity : AppCompatActivity(), CharacterAdapter.EndReachedCallback {

    private lateinit var viewModel: CharacterViewModel
    private lateinit var recyclerView: RecyclerView

    private val adapter: CharacterAdapter = CharacterAdapter(this)
    private val disposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recylerview)

        viewModel = ViewModelProvider(this).get(CharacterViewModel::class.java)
        viewModel.characterLiveData.observe(this, Observer { list -> adapter.setCharacterList(list) })
        viewModel.isEndLiveData.observe(this, Observer { adapter.setDoneLoading() })

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter

        disposable.add(adapter.getRowClickedObservable().observeOn(AndroidSchedulers.mainThread())
            .subscribe { character ->
                val intent = Intent(this, CharacterActivity::class.java)
                intent.putExtra(USER_INTENT, character)
                startActivity(intent)
            })
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    override fun endReached() {
        viewModel.getCharacters()
    }

    companion object {
        const val USER_INTENT = "USER_INTENT"
    }
}
