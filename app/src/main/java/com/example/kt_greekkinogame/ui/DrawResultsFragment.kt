package com.example.kt_greekkinogame.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kt_greekkinogame.databinding.FragmentDrawResultsBinding
import com.example.kt_greekkinogame.network.NetworkModule
import com.example.kt_greekkinogame.repository.DrawRepository
import com.example.kt_greekkinogame.viewmodel.DrawViewModel
import com.example.kt_greekkinogame.viewmodel.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

class DrawResultsFragment : Fragment() {

    private var _binding: FragmentDrawResultsBinding? = null
    private val binding get() = _binding!!

    private val repository by lazy { DrawRepository(NetworkModule.apiService) }
    private val viewModel: DrawViewModel by viewModels { ViewModelFactory(repository) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DrawResultAdapter()
        binding.recyclerViewResults.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewResults.adapter = adapter

        viewModel.results.observe(viewLifecycleOwner) { results ->
            adapter.submitList(results)
        }

        binding.fabDatePicker.setOnClickListener {
            showDatePicker()
        }

        // Fetch today's results by default
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = sdf.format(Date())
        viewModel.fetchResults(1100, todayDate, todayDate)
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDay, 0, 0, 0)
                    set(Calendar.MILLISECOND, 0)
                }.time

                // Ensure the selected date is not in the future
                if (selectedDate.after(Calendar.getInstance().time)) {
                    Toast.makeText(requireContext(), "Cannot select a future date", Toast.LENGTH_SHORT).show()
                } else {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val dateString = sdf.format(selectedDate)
                    viewModel.updateDate(dateString)
                }
            },
            year, month, day
        )

        datePickerDialog.datePicker.maxDate = calendar.timeInMillis
        datePickerDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
