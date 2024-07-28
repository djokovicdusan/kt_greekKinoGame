package com.example.kt_greekkinogame.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.kt_greekkinogame.R
import com.example.kt_greekkinogame.databinding.ActivityMainBinding
import com.example.kt_greekkinogame.repository.DrawRepository
import com.example.kt_greekkinogame.network.NetworkModule
import com.example.kt_greekkinogame.viewmodel.MainViewModel
import com.example.kt_greekkinogame.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val repository by lazy { DrawRepository(NetworkModule.apiService) }
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_draw_list -> {
                    replaceFragment(DrawListFragment())
                    true
                }
                R.id.navigation_draw_animation -> {
                    replaceFragment(DrawAnimationFragment())
                    true
                }
                R.id.navigation_draw_results -> {
                    replaceFragment(DrawResultsFragment())
                    true
                }
                else -> false
            }
        }

        // Set the default fragment
        if (savedInstanceState == null) {
            binding.navView.selectedItemId = R.id.navigation_draw_list
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_activity_main, fragment)
            .commit()
    }
}
