package com.example.gorentapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gorentapps.databinding.ItemCardviewCarBinding
import com.example.gorentapps.model.Car

class CardViewCarAdapter(private val context: Context): RecyclerView.Adapter<CardViewCarAdapter.CardViewViewHolder>() {

    var listCars = arrayListOf<Car>()
    private lateinit var mListener: OnItemClickListener

    // 1
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    inner class CardViewViewHolder(binding: ItemCardviewCarBinding):RecyclerView.ViewHolder(binding.root){
        val imgPhoto = binding.imgItemPhoto
        val name = binding.tvName
        val passenger = binding.tvPassenger
        val rentalPrice = binding.tvRentalPrice
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewViewHolder {
        val binding: ItemCardviewCarBinding = ItemCardviewCarBinding.inflate(LayoutInflater.from(context), parent, false)
        return CardViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewViewHolder, position: Int) {
        val car = listCars[position]

        Glide.with(holder.itemView.context)
            .load(car.image)
            .apply(RequestOptions())
            .into(holder.imgPhoto)

        holder.name.text = car.name
        holder.passenger.text = car.passenger
        holder.rentalPrice.text = car.rentalPrice

        holder.itemView.setOnClickListener {
            mListener.onItemClick(position)
        }
    }

    override fun getItemCount() = listCars.size

}