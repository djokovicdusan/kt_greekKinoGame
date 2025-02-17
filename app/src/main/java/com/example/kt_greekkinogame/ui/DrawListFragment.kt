package com.example.kt_greekkinogame.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kt_greekkinogame.R
import com.example.kt_greekkinogame.databinding.FragmentDrawListBinding
import com.example.kt_greekkinogame.model.Draw
import com.example.kt_greekkinogame.network.NetworkModule
import com.example.kt_greekkinogame.repository.DrawRepository
import com.example.kt_greekkinogame.viewmodel.DrawViewModel
import com.example.kt_greekkinogame.viewmodel.ViewModelFactory

class DrawListFragment : Fragment(), DrawAdapter.OnItemClickListener {

    private val repository by lazy { DrawRepository(NetworkModule.apiService) }
    private val viewModel: DrawViewModel by viewModels { ViewModelFactory(repository) }
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


        val adapter = DrawAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.draws.observe(viewLifecycleOwner) { draws ->
            adapter.submitList(draws)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.fetchUpcomingDraws(1100)
        }

        viewModel.fetchUpcomingDraws(1100)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(draw: Draw) {
        val fragment = DrawDetailFragment().apply {
            arguments = Bundle().apply {
                putSerializable("draw", draw)
            }
        }
        parentFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}
