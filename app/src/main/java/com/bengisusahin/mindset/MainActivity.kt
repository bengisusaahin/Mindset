package com.bengisusahin.mindset

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.bengisusahin.mindset.databinding.ActivityMainBinding
import com.bengisusahin.mindset.fragment.GivingFragment
import com.bengisusahin.mindset.fragment.HappinessFragment
import com.bengisusahin.mindset.fragment.HomeFragment
import com.bengisusahin.mindset.fragment.KindnessFragment
import com.bengisusahin.mindset.fragment.OptimismFragment
import com.bengisusahin.mindset.fragment.RespectFragment

class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    val binding: ActivityMainBinding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, HomeFragment())
                .commit()
        }

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navigateToDetail(HomeFragment())

                    true
                }
                R.id.switchHappiness -> {
                    navigateToDetail(HappinessFragment())
                    true
                }

                R.id.switchOptimism -> {
                    navigateToDetail(OptimismFragment())
                    true
                }

                R.id.switchKindness -> {
                    navigateToDetail(KindnessFragment())
                    true
                }

                R.id.switchGiving -> {
                    navigateToDetail(GivingFragment())
                    true
                }

                R.id.switchRespect -> {
                    navigateToDetail(RespectFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun navigateToDetail(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}
