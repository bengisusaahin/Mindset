package com.bengisusahin.mindset.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bengisusahin.mindset.MainActivity
import com.bengisusahin.mindset.R
import com.bengisusahin.mindset.databinding.FragmentHomeBinding
import com.bengisusahin.mindset.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    private val addedItemsSet = mutableSetOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.isEgoChecked.observe(viewLifecycleOwner) { isChecked ->
            binding.switchEgo.isChecked = isChecked
            updateBottomNavigationVisibility(isChecked)
            updateSwitchesState()
        }

        homeViewModel.addedItemsSet.observe(viewLifecycleOwner) { addedItems ->
            updateSwitchesState()
        }

        // Listener for the Ego switch
        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            homeViewModel.setEgoChecked(isChecked) // Update ViewModel with new state
            if (isChecked) {
                resetSwitchesAndBottomNav() // Reset other switches and bottom navigation
            }
        }
    }

    private fun updateSwitchesState() {
        val isEgoChecked = homeViewModel.isEgoChecked.value ?: true

        binding.apply {
            val switches = listOf(
                Triple(switchHappiness, R.drawable.ic_happiness, getString(R.string.happiness)),
                Triple(switchOptimism, R.drawable.ic_optimism, getString(R.string.optimism)),
                Triple(switchKindness, R.drawable.ic_kindness, getString(R.string.kindness)),
                Triple(switchGiving, R.drawable.ic_giving, getString(R.string.giving)),
                Triple(switchRespect, R.drawable.ic_respect, getString(R.string.respect))
            )

            // Enable/disable switches based on Ego switch state
            switches.forEach { (switch, iconResId, title) ->
                switch.setOnCheckedChangeListener(null)

                switch.isChecked = if (isEgoChecked) false else (homeViewModel.addedItemsSet.value?.contains(switch.id) == true)
                switch.isEnabled = !isEgoChecked 

                switch.setOnCheckedChangeListener { _, isChecked ->
                    val bottomNavigationView = (activity as MainActivity).binding.bottomNavigation
                    val currentSwitchCount = bottomNavigationView.menu.size()

                    if (isChecked) {
                        if (currentSwitchCount < 5 && !homeViewModel.addedItemsSet.value!!.contains(switch.id)) {
                            bottomNavigationView.menu.add(Menu.NONE, switch.id, Menu.NONE, title)
                                .setIcon(iconResId)
                            homeViewModel.addItem(switch.id)
                        } else if (currentSwitchCount >= 5) {
                            Toast.makeText(
                                context,
                                "Maximum switch limit reached.",
                                Toast.LENGTH_SHORT
                            ).show()
                            switch.isChecked = false
                        } else {
                            Toast.makeText(
                                context,
                                "$title already added.",
                                Toast.LENGTH_SHORT
                            ).show()
                            switch.isChecked = false
                        }
                    } else {
                        bottomNavigationView.menu.removeItem(switch.id)
                        homeViewModel.removeItem(switch.id)
                    }
                }
            }
        }
    }

    private fun updateBottomNavigationVisibility(isEgoChecked: Boolean) {
        (activity as MainActivity).binding.bottomNavigation.visibility =
            if (isEgoChecked) View.GONE else View.VISIBLE
    }

    private fun resetSwitchesAndBottomNav() {
        val bottomNavigationView = (activity as MainActivity).binding.bottomNavigation
        
        for (i in bottomNavigationView.menu.size() - 1 downTo 0) {
            val itemId = bottomNavigationView.menu.getItem(i).itemId

            if (itemId != R.id.navigation_home) {
                bottomNavigationView.menu.removeItem(itemId)
                homeViewModel.removeItem(itemId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}