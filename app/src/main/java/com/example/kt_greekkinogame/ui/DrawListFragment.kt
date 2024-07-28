package com.example.kt_greekkinogame.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.kt_greekkinogame.ui.DrawAdapter
import com.example.kt_greekkinogame.databinding.FragmentDrawListBinding
import com.example.kt_greekkinogame.repository.DrawRepository
import com.example.kt_greekkinogame.network.NetworkModule
import com.example.kt_greekkinogame.viewmodel.MainViewModel
import com.example.kt_greekkinogame.viewmodel.MainViewModelFactory

class DrawListFragment : Fragment() {

    private val repository by lazy { DrawRepository(NetworkModule.apiService) }
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(repository) }
    private var _binding: FragmentDrawListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("DrawListFragment", "onViewCreated called")

        val adapter = DrawAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.draws.observe(viewLifecycleOwner) { draws ->
            Log.d("DrawListFragment", "Observed draws: $draws")
            adapter.submitList(draws)
            binding.swipeRefreshLayout.isRefreshing = false // Stop the refreshing animation
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            Log.d("DrawListFragment", "Refreshing data")
            viewModel.fetchUpcomingDraws(1100) // Trigger data refresh
        }

        viewModel.fetchUpcomingDraws(1100)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
