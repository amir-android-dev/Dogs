package com.amir.dogs.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import com.amir.dogs.R
import com.amir.dogs.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {
    private var dogUuid = 0
    lateinit var viewModel: DetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.fetch()

        arguments?.let {
            dogUuid = DetailFragmentArgs.fromBundle(it).dogUid

        }

        observerViewModel()

    }

    @SuppressLint("FragmentLiveDataObserve")
    private fun observerViewModel() {
        viewModel.dogLiveData.observe(this, Observer {dog->
            dog?.let {
              tv_dogName.text =dog.dogBreed
                  tv_dogPurpose.text =dog.lifeSpan
                  tv_dogTemperament.text =dog.bredFor
                  tv_dogLifespan.text = dog.temperament


            }

        })
    }


}