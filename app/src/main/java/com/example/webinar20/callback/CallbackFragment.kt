package com.example.webinar20.callback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.webinar20.databinding.FragmentCallbackBinding
import kotlinx.coroutines.launch

class CallbackFragment : Fragment() {

    private var _binding: FragmentCallbackBinding? = null
    private val binding get() = _binding!!
    private val viewModel = CallbackViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCallbackBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.launchRequest.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val result =
                        viewModel.fetchDataWithRetrofit("b54b16e1-ac3b-4bff-a11f-f7ae9ddc27e0")
                    binding.result.text = result.name
                } catch (e: Exception) {
                    binding.result.text = e.message
                }


            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}