package com.example.facedetection.noFacesImages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.facedetection.R
import com.example.facedetection.noFacesImages.model.NoFacesImageUiModel
import com.example.facedetection.noFacesImages.adapter.NoFacesImagesAdapter
import kotlinx.android.synthetic.main.fragment_no_faces.*
import kotlinx.android.synthetic.main.top_title_fragment.view.*

class NoFacesImagesFragment : Fragment() {
    private lateinit var noFacesImagesViewModel: NoFacesImagesViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_no_faces, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
    }

    private fun initViewModel() {
        noFacesImagesViewModel = ViewModelProviders.of(this).get(NoFacesImagesViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        noFacesImagesViewModel
            .getImagesWithoutFacesLiveData()
            .observe(this, Observer { onImagesReceived(it.map { NoFacesImageUiModel(it) }) })
    }

    private fun onImagesReceived(noFaceimages: List<NoFacesImageUiModel>) {
        no_faces_images_rv.adapter = NoFacesImagesAdapter(noFaceimages)
    }

    private fun initViews() {
        setTitle()
        setRecycler()
    }

    private fun setRecycler() {
        no_faces_images_rv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setTitle() {
        top_title_no_faces.title.text = getString(R.string.no_faces_images)
    }
}
