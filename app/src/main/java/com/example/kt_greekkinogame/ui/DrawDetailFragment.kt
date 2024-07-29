package com.example.kt_greekkinogame.ui

import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.kt_greekkinogame.R
import com.example.kt_greekkinogame.databinding.FragmentDrawDetailBinding
import com.example.kt_greekkinogame.model.PrizeCategory
import java.text.SimpleDateFormat
import java.util.*

class DrawDetailFragment : Fragment() {

    private var _binding: FragmentDrawDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DrawDetailFragmentArgs by navArgs()
    private var countDownTimer: CountDownTimer? = null
    private val selectedNumbers = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDrawDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val draw = args.draw ?: return

        val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val drawTime = Date(draw.drawTime)
        val drawTimeString = format.format(drawTime)
        val drawDetails = "Draw Time: $drawTimeString\nDraw ID: ${draw.drawId}"

        binding.drawDetails.text = drawDetails

        draw.prizeCategories?.let {
            populatePrizeCategoriesTable(it)
        }

        populateBallSelectionGrid()
        updateRemainingTime(draw.drawTime)
    }

    private fun populatePrizeCategoriesTable(prizeCategories: List<PrizeCategory>) {
        binding.prizeCategoriesTable.removeAllViews()

        val headerRow = TableRow(context)
        headerRow.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        val headers = listOf("B.No") + prizeCategories.map { it.id.toString() }
        headers.forEach { header ->
            val textView = TextView(context).apply {
                text = header
                setTextColor(resources.getColor(android.R.color.white))
                setTextSize(14f)
                setPadding(8, 8, 8, 8)
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                width = 210
                setBackgroundResource(R.drawable.table_cell_border)
            }
            headerRow.addView(textView)
        }

        binding.prizeCategoriesTable.addView(headerRow)

        // Create data row
        val dataRow = TableRow(context)
        dataRow.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT
        )

        // Add prize data
        val prizeData = listOf("Fix") + prizeCategories.map { it.fixed.toString() }
        prizeData.forEach { data ->
            val textView = TextView(context).apply {
                text = data
                setTextColor(resources.getColor(android.R.color.holo_orange_light))
                setTextSize(14f) // Smaller text size
                setPadding(8, 8, 8, 8)
                textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                width = 150 // Set fixed width for each cell
                setBackgroundResource(R.drawable.table_cell_border)
            }
            dataRow.addView(textView)
        }

        binding.prizeCategoriesTable.addView(dataRow)
    }

    private fun populateBallSelectionGrid() {
        val buttonSize = resources.displayMetrics.widthPixels / 10 - 10

        for (i in 1..80) {
            val button = Button(context).apply {
                text = i.toString()
                setBackgroundColor(Color.DKGRAY)
                setTextColor(Color.WHITE)
                textSize = 14f
                layoutParams = ViewGroup.LayoutParams(buttonSize, buttonSize)
                setOnClickListener {
                    isSelected = !isSelected
                    if (isSelected) {
                        setBackgroundColor(Color.BLACK)
                        selectedNumbers.add(i)
                    } else {
                        setBackgroundColor(Color.DKGRAY)
                        selectedNumbers.remove(i)
                    }
                    updateSelectedNumbersDisplay()
                }
            }
            binding.ballSelectionGrid.addView(button)
        }
    }

    private fun updateSelectedNumbersDisplay() {
        binding.selectedNumbers.text = "Selected Numbers: ${selectedNumbers.joinToString(", ")}"
    }

    private fun updateRemainingTime(drawTimeInMillis: Long) {
        countDownTimer?.cancel()

        val currentTime = System.currentTimeMillis()
        val timeRemaining = drawTimeInMillis - currentTime

        if (timeRemaining > 0) {
            countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val seconds = millisUntilFinished / 1000 % 60
                    val minutes = millisUntilFinished / 1000 / 60 % 60
                    val hours = millisUntilFinished / 1000 / 60 / 60 % 24
                    binding.remainingTime.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                }

                override fun onFinish() {
                    binding.remainingTime.text = "00:00:00"
                }
            }.start()
        } else {
            binding.remainingTime.text = "00:00:00"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        countDownTimer?.cancel()
    }
}
