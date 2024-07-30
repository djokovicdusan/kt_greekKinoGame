package com.example.kt_greekkinogame.ui

import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kt_greekkinogame.R
import com.example.kt_greekkinogame.databinding.ItemDrawResultBinding
import com.example.kt_greekkinogame.model.DrawResult
import java.text.SimpleDateFormat
import java.util.*

class DrawResultAdapter : ListAdapter<DrawResult, DrawResultAdapter.DrawResultViewHolder>(DrawResultDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawResultViewHolder {
        val binding = ItemDrawResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrawResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DrawResultViewHolder(private val binding: ItemDrawResultBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(drawResult: DrawResult) {
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
            val drawTime = Date(drawResult.drawTime)
            binding.drawTimeText.text = "Draw Time: ${format.format(drawTime)}"
            binding.drawIdText.text = "Draw ID: ${drawResult.drawId}"

            val winningNumbers = drawResult.winningNumbers.list.sorted()

            binding.winningNumbersLayout.removeAllViews()
            val rows = mutableListOf<LinearLayout>()
            for (i in 0 until 3) {
                val row = LinearLayout(binding.root.context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    orientation = LinearLayout.HORIZONTAL
                    gravity = Gravity.CENTER
                }
                rows.add(row)
                binding.winningNumbersLayout.addView(row)
            }

            val numberDistribution = listOf(7, 7, 7)
            var index = 0
            for ((rowIndex, count) in numberDistribution.withIndex()) {
                for (i in 0 until count) {
                    val ball = TextView(binding.root.context).apply {
                        text = if (index < winningNumbers.size) winningNumbers[index].toString() else ""
                        textSize = 14f
                        setPadding(8, 8, 8, 8)
                        background = ContextCompat.getDrawable(binding.root.context, R.drawable.circle_background)
                        gravity = Gravity.CENTER
                        layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f).apply {
                            marginEnd = 4
                            marginStart = 4
                        }
                        visibility = if (index < winningNumbers.size) View.VISIBLE else View.INVISIBLE
                    }
                    rows[rowIndex].addView(ball)
                    index++
                }
            }

            binding.bonusNumbersText.text = "Bonus Numbers: ${drawResult.winningNumbers.bonus.joinToString(", ")}"
        }
    }

    class DrawResultDiffCallback : DiffUtil.ItemCallback<DrawResult>() {
        override fun areItemsTheSame(oldItem: DrawResult, newItem: DrawResult): Boolean {
            return oldItem.drawId == newItem.drawId
        }

        override fun areContentsTheSame(oldItem: DrawResult, newItem: DrawResult): Boolean {
            return oldItem == newItem
        }
    }
}
