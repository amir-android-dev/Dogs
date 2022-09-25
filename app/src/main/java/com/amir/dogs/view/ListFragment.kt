package com.amir.dogs.view

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.TextViewOnReceiveContentListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.amir.dogs.R
import com.amir.dogs.viewmodel.ListViewModel

import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_list.*
class ListFragment : Fragment() {
    lateinit var viewModel: ListViewModel
    val dogsListAdapter = DogListAdapter(arrayListOf())

    lateinit var dogList:RecyclerView
    lateinit var listError: TextView
    lateinit var progressBar: ProgressBar
    lateinit var   refreshLayout : SwipeRefreshLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         dogList = view.findViewById(R.id.rec_dog_list)
         listError = view.findViewById(R.id.tv_list_error)
         progressBar = view.findViewById(R.id.progress_list)
        refreshLayout =view.findViewById(R.id.refresh_layout)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.refresh()

        dogList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dogsListAdapter
        }
        refresh_layout.setOnRefreshListener {
            dogList.visibility = View.GONE
            listError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            viewModel.refresh()
            refreshLayout.isRefreshing = false

        }
        observeViewModel()
    }

    //it use the variable inside of our created viewModel to update the layout base on the values that we get
    @SuppressLint("FragmentLiveDataObserve")
    private fun observeViewModel() {
        viewModel.dogs.observe(this, Observer {dogs->
            dogs?.let {
              dogList.visibility = View.VISIBLE
                dogsListAdapter.updateDogsList(dogs)
            }
        })
        viewModel.dogsLoadError.observe(this, Observer {isError->
         isError?.let {
             listError.visibility = if(it) View.VISIBLE else View.GONE
         }
        })
        viewModel.loading.observe(this, Observer { isLoading->
            isLoading?.let {
                progressBar.visibility = if(it) View.VISIBLE else View.GONE
                //show and hide other views based on this spinner
                if(it){
                    listError.visibility = View.GONE
                    dogList.visibility = View.GONE
                }
            }
        } )
    }
}