package com.amir.dogs.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.amir.dogs.R
import com.amir.dogs.model.DogBreed
import kotlinx.android.synthetic.main.item_dog.view.*

class DogListAdapter(private val dogList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
//        holder.view.tv_name.text = dogList[position].dogBreed
//        holder.view.tv_lifespan.text = dogList[position].lifeSpan
        holder.name.text = dogList[position].dogBreed
        holder.lifespan.text = dogList[position].lifeSpan

        holder.view.setOnClickListener {
            val action = ListFragmentDirections.actionDetailFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDogsList(newDogsList: List<DogBreed>) {
        dogList.clear()
        dogList.addAll(newDogsList)
        notifyDataSetChanged()
    }
}

class DogViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.tv_name)!!
    val lifespan = view.findViewById<TextView>(R.id.tv_lifespan)!!
}