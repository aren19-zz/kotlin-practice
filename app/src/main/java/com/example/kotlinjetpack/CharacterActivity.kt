package com.example.kotlinjetpack

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kotlinjetpack.data.Character

class CharacterActivity : AppCompatActivity() {

    private var character: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_main)

        character = intent?.extras?.getParcelable(MainActivity().userIntent)

        showUi()
    }

    private fun showUi() {
        if (character != null) {
            if (character?.image != null) {
                val image: ImageView = findViewById(R.id.image)
                Glide.with(image.context).load(character?.image).into(image)
            }
            findViewById<TextView>(R.id.name).text = character?.name
            findViewById<TextView>(R.id.species).text = character?.species
            findViewById<TextView>(R.id.status).text = character?.status
        }
    }
}