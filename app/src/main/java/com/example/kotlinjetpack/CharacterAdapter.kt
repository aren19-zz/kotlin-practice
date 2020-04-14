package com.example.kotlinjetpack

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlinjetpack.data.Character
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CharacterAdapter(private val endReachedCallback: EndReachedCallback) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val typeLoading = 0
    private val typeCharacter = 1
    private var isDoneLoading = false
    private var list: MutableList<Character> = mutableListOf()
    private val rowClickedPublishSubject: PublishSubject<Character> = PublishSubject.create()

    interface EndReachedCallback {
        fun endReached()
    }

    fun getRowClickedObservable(): Observable<Character> {
        return rowClickedPublishSubject.hide()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == typeCharacter)
            CharacterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row, parent, false))
        else
            LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.loading_row, parent, false))
    }

    override fun getItemCount(): Int {
        if (isDoneLoading) {
            return list.size
        }
        return list.size + 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CharacterViewHolder) {
            holder.bind(list[position], rowClickedPublishSubject)
        } else {
            endReachedCallback.endReached()
        }
    }

    override fun getItemViewType(position: Int): Int = if (position == list.size) {
        typeLoading
    } else {
        typeCharacter
    }

    fun setCharacterList(characterList: List<Character>) {
        list.clear()
        list.addAll(characterList)
        notifyDataSetChanged()
    }

    fun setDoneLoading() {
        isDoneLoading = true
        notifyItemRemoved(list.size)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.image)
        private val textView: TextView = itemView.findViewById(R.id.name)
        private val container: ConstraintLayout = itemView.findViewById(R.id.container)

        fun bind(character: Character, rowClickedPublishSubject: PublishSubject<Character>) {
            if (!TextUtils.isEmpty(character.image)) {
                Glide.with(imageView.context).load(character.image).into(imageView)
            }
            textView.text = character.name
            container.setOnClickListener { rowClickedPublishSubject.onNext(character) }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}