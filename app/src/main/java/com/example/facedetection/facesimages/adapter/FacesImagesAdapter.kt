package com.example.facedetection.facesimages.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.facedetection.R
import com.example.facedetection.facesimages.model.FacesImageUiModel
import kotlinx.android.synthetic.main.list_image_item.view.*

class FacesImagesAdapter(private val data: List<FacesImageUiModel>) :
    RecyclerView.Adapter<FacesImagesAdapter.FacesImagesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacesImagesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_image_item, parent, false)


        return FacesImagesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: FacesImagesViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class FacesImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(facesImageUiModel: FacesImageUiModel) {
            itemView.image.setImageBitmap(facesImageUiModel.image)
        }

    }
}