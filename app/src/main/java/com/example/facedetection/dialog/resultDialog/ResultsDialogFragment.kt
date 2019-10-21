package com.example.facedetection.dialog.resultDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.example.facedetection.R
import kotlinx.android.synthetic.main.fragment_results_dialog.*

class ResultsDialogFragment : DialogFragment() {

    private lateinit var resultsDialogViewModel: ResultsDialogViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_results_dialog, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }


    private fun initViewModel() {
        resultsDialogViewModel = ViewModelProviders.of(this).get(ResultsDialogViewModel::class.java)
    }

    private fun initViews() {
        setTextResult()
        setBtn()
    }

    private fun setBtn() {
        btn_ok.setOnClickListener {dismiss() }
    }

    private fun setTextResult() {
        text_result.text = resultsDialogViewModel.getText()
    }
}