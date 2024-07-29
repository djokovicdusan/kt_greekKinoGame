package com.example.kt_greekkinogame.ui

import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kt_greekkinogame.databinding.ItemDrawBinding
import com.example.kt_greekkinogame.model.Draw
import java.text.SimpleDateFormat
import java.util.*

class DrawAdapter(private val listener: OnItemClickListener) : ListAdapter<Draw, DrawAdapter.DrawViewHolder>(DrawDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawViewHolder {
        val binding = ItemDrawBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DrawViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DrawViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun submitList(list: List<Draw>?) {
        super.submitList(list)
    }

    inner class DrawViewHolder(private val binding: ItemDrawBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private var countDownTimer: CountDownTimer? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(draw: Draw) {
            Log.d("DrawAdapter", "Binding draw: $draw")
            val drawTime = Date(draw.drawTime)
            val format = SimpleDateFormat("MMM dd, yyyy - HH:mm", Locale.getDefault())
            binding.drawTime.text = "Draw Time: ${format.format(drawTime)}"

            val currentTime = System.currentTimeMillis()
            val timeRemaining = draw.drawTime - currentTime

            countDownTimer?.cancel()

            if (timeRemaining > 0) {
                countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val seconds = millisUntilFinished / 1000 % 60
                        val minutes = millisUntilFinished / 1000 / 60 % 60
                        val hours = millisUntilFinished / 1000 / 60 / 60 % 24
                        binding.timer.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                    }

                    override fun onFinish() {
                        binding.timer.text = "00:00:00"
                    }
                }.start()
            } else {
                binding.timer.text = "00:00:00"
            }

            // Change text color based on draw status
            if (draw.status == "active") {
                binding.timer.setTextColor(binding.root.context.getColor(android.R.color.holo_red_dark))
            } else {
                binding.timer.setTextColor(binding.root.context.getColor(android.R.color.holo_green_dark))
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(getItem(position))
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(draw: Draw)
    }
}

class DrawDiffCallback : DiffUtil.ItemCallback<Draw>() {
    override fun areItemsTheSame(oldItem: Draw, newItem: Draw): Boolean {
        return oldItem.drawId == newItem.drawId
    }

    override fun areContentsTheSame(oldItem: Draw, newItem: Draw): Boolean {
        return oldItem == newItem
    }
}
