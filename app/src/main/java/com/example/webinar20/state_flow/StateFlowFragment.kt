package com.example.webinar20.state_flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.webinar20.databinding.FragmentStateFlowBinding

class StateFlowFragment : Fragment() {

    private var _binding: FragmentStateFlowBinding? = null
    private val viewModel = StateFlowViewModel()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStateFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.increment.setOnClickListener {
            viewModel.increment()
        }
        binding.decrement.setOnClickListener {
            viewModel.decrement()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.screenStateFlow.collect {
                binding.result.text = "${it.value} ${it.message}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}