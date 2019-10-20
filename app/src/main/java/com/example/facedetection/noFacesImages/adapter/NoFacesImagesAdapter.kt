package com.example.facedetection.noFacesImages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.facedetection.R
import com.example.facedetection.noFacesImages.model.NoFacesImageUiModel
import kotlinx.android.synthetic.main.list_image_item.view.*

class NoFacesImagesAdapter(private val data: List<NoFacesImageUiModel>) :
    RecyclerView.Adapter<NoFacesImagesAdapter.NoFacesImagesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoFacesImagesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_image_item, parent, false)


        return NoFacesImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NoFacesImagesViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class NoFacesImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(noFacesImageUiModel: NoFacesImageUiModel) {
            itemView.image.setImageBitmap(noFacesImageUiModel.image)
        }

    }
}