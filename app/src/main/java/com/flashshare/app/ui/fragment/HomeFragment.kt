package com.flashshare.app.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.flashshare.app.R
import com.flashshare.app.databinding.FragmentHomeBinding
import com.flashshare.app.ui.viewmodel.DiscoveryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val discoveryViewModel: DiscoveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.sendButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_fileSelectorFragment)
        }

        binding.receiveButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_receiveFragment)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            discoveryViewModel.discoveredDevices.collect { devices ->
                // Update UI with discovered devices
            }
        }
    }
}
