package com.example.facedetection.allImages


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.facedetection.R
import com.example.facedetection.allImages.adapter.ImagesAdapter
import com.example.facedetection.allImages.model.ImageUiModel
import kotlinx.android.synthetic.main.fragment_all.*
import kotlinx.android.synthetic.main.top_title_fragment.view.*

class AllImagesFragment : Fragment() {

    private val MAX_PICTURES = 1000
    private var pageCounter = 1

    private lateinit var allImagesViewModel: AllImagesViewModel
    private val adapter: ImagesAdapter = ImagesAdapter()

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
        allImagesViewModel.getImagesByPage(pageCounter)
    }

    private fun initViewModel() {
        allImagesViewModel = ViewModelProviders.of(this).get(AllImagesViewModel::class.java)
        setObservers()
    }

    private fun setObservers() {
        allImagesViewModel
            .imageListLiveData
            .observe(this, Observer { onImagesReceived(it) })
    }

    private fun onImagesReceived(imageList: List<ImageUiModel>) {

        adapter.data.addAll(imageList)
        adapter.notifyDataSetChanged()
    }

    private fun initViews() {
        setTitle()
        setBtnDetect()
        setRecycler()
    }

    private fun setBtnDetect() {
        btn_detect.setOnClickListener {
            allImagesViewModel.startDetection(adapter.data)
        }
    }

    private fun setRecycler() {
        all_images_rv.adapter = adapter

        all_images_rv.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            onScrollToEnd(layoutManager as GridLayoutManager) { loadMoreImages() }
        }


    }

    private fun loadMoreImages() {
        allImagesViewModel.isLoadingLiveData.value?.let {
            if (it || all_images_rv.layoutManager?.itemCount == MAX_PICTURES) return
            allImagesViewModel.getImagesByPage(pageCounter++)
        }
    }


    private fun RecyclerView.onScrollToEnd(
        gridLayoutManager: GridLayoutManager,
        onScrollNearEnd: (Unit) -> Unit
    ) = addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (gridLayoutManager.itemCount - 3 == gridLayoutManager.findLastVisibleItemPosition()) {  //if near fifth item from end
                onScrollNearEnd(Unit)
            }
        }
    })

    private fun setTitle() {
        top_title_all.title.text = getString(R.string.all_images)
    }

}