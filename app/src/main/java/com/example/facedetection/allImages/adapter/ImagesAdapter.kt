package com.example.facedetection.allImages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.facedetection.R
import com.example.facedetection.allImages.model.ImageUiModel
import kotlinx.android.synthetic.main.list_image_item.view.*

class ImagesAdapter(private val data: List<ImageUiModel>) :
    RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_image_item, parent, false)


        return ImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUiModel: ImageUiModel) {
            Glide.with(itemView.context).load(imageUiModel.imageUrl)
                .apply(RequestOptions().placeholder(R.drawable.ic_faces)).into(itemView.image)
        }

    }
}