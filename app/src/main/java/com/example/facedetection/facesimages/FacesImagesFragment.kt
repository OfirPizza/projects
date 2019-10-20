package com.example.facedetection.facesimages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.facedetection.R
import com.example.facedetection.facesimages.adapter.FacesImagesAdapter
import com.example.facedetection.facesimages.model.FacesImageUiModel
import kotlinx.android.synthetic.main.fragment_faces.*
import kotlinx.android.synthetic.main.top_title_fragment.view.*

class FacesImagesFragment : Fragment() {

    private lateinit var facesImagesViewModel: FacesImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_faces, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }


    private fun initViewModel() {
        facesImagesViewModel = ViewModelProviders.of(this).get(FacesImagesViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        facesImagesViewModel
            .getFacesImagesLiveData()
            .observe(this, Observer { onImagesReceived(it.map { FacesImageUiModel(it) }) })
    }

    private fun onImagesReceived(faceimages: List<FacesImageUiModel>) {
        faces_images_rv.adapter = FacesImagesAdapter(faceimages)
    }

    private fun initViews() {
        setTitle()
        setRecycler()
    }

    private fun setRecycler() {
        faces_images_rv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setTitle() {
        top_title_faces.title.text = getString(R.string.faces_images)
    }


}
