package com.bengisusahin.mindset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
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

        binding.switchEgo.setOnCheckedChangeListener { _, isChecked ->
            updateSwitchesState()
            (activity as MainActivity).binding.bottomNavigation.visibility =
                if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun updateSwitchesState() {
        val isEgoChecked = binding.switchEgo.isChecked

        binding.apply {
            val switches = listOf(switchHappiness, switchOptimism, switchKindness, switchGiving, switchRespect)

            // Enable/disable switches based on Ego switch state
            switches.forEach { switch ->
                switch.isChecked = false
                switch.isEnabled = !isEgoChecked
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}