package com.example.facedetection.allImages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.facedetection.R
import com.example.facedetection.allImages.adapter.ImagesAdapter
import com.example.facedetection.allImages.model.ImageUiModel
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.android.synthetic.main.top_title_fragment.view.*

class AllImagesFragment : Fragment() {

    private lateinit var mAllImagesViewModel: AllImagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initViewModel()
        fetchData()
    }

    private fun fetchData() {
        mAllImagesViewModel.getAllImages()
    }

    private fun initViewModel() {
        mAllImagesViewModel = ViewModelProviders.of(this).get(AllImagesViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        mAllImagesViewModel
            .imageListLiveData
            .observe(this, Observer { onImagesReceived(it) })
    }

    private fun onImagesReceived(imageList: List<ImageUiModel>) {
        all_images_rv.adapter = ImagesAdapter(imageList)
    }

    private fun initViews() {
        setTitle()
        setBtnDetect()
        setRecycler()
    }

    private fun setBtnDetect() {
        btn_detect.setOnClickListener {
            mAllImagesViewModel.startDetection()
        }
    }

    private fun setRecycler() {
        all_images_rv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setTitle() {
        top_title.title.text = getString(R.string.all_images)
    }


}
