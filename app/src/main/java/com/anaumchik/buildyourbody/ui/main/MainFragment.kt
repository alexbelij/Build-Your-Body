package com.anaumchik.buildyourbody.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anaumchik.buildyourbody.R
import org.koin.android.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainFragmentViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)
}