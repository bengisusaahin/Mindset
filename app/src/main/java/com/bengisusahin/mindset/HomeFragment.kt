package com.bengisusahin.mindset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import com.bengisusahin.mindset.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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

        binding.switchEgo.isChecked = true
        updateSwitchesState()

        (activity as MainActivity).binding.bottomNavigation.visibility =
            if (binding.switchEgo.isChecked) View.GONE else View.VISIBLE

        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchesState()
            (activity as MainActivity).binding.bottomNavigation.visibility =
                if (isChecked) View.GONE else View.VISIBLE
        }
    }

    private fun updateSwitchesState() {
        val isEgoChecked = binding.switchEgo.isChecked

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

                switch.isChecked = false
                switch.isEnabled = !isEgoChecked

                switch.setOnCheckedChangeListener { _, isChecked ->
                    val bottomNavigationView = (activity as MainActivity).binding.bottomNavigation
                    if (isChecked) {
                        bottomNavigationView.menu.add(Menu.NONE, switch.id, Menu.NONE, title)
                            .setIcon(iconResId)
                    } else {
                        bottomNavigationView.menu.removeItem(switch.id)
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}