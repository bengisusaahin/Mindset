package com.bengisusahin.mindset

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bengisusahin.mindset.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.switchEgo.isChecked = true
        updateSwitchesState()
        updateBottomNavigationVisibility()

        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchesState()
            updateBottomNavigationVisibility()
        }
    }

    private fun updateSwitchesState() {
        val isEgoChecked = binding.switchEgo.isChecked

        binding.apply {
            if (isEgoChecked) {
                switchHappiness.isChecked = false
                switchOptimism.isChecked = false
                switchKindness.isChecked = false
                switchGiving.isChecked = false
                switchRespect.isChecked = false
            }

            switchHappiness.isEnabled = !isEgoChecked
            switchOptimism.isEnabled = !isEgoChecked
            switchKindness.isEnabled = !isEgoChecked
            switchGiving.isEnabled = !isEgoChecked
            switchRespect.isEnabled = !isEgoChecked
        }
    }

    private fun updateBottomNavigationVisibility() {
        val isEgoChecked = binding.switchEgo.isChecked
        binding.bottomNavigation.visibility = if (isEgoChecked) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}