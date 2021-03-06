package com.jscode.bipolarfactorydemo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.jscode.bipolarfactorydemo.R
import com.jscode.bipolarfactorydemo.adapter.ImagesAdapter
import com.jscode.bipolarfactorydemo.databinding.FragmentItemsBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ItemsFragment : Fragment() {
    companion object {
        @JvmStatic
        fun newInstance() = ItemsFragment()
    }

    private val viewModel: ItemsViewModel by lazy {
        ViewModelProvider(this).get(ItemsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentItemsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_items, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.model = viewModel
        val adapter = ImagesAdapter()
        binding.imageList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.imageList.adapter = adapter
        lifecycleScope.launch {
            viewModel.getResult().collect {
                if (binding.loading.visibility == View.VISIBLE) binding.loading.visibility =
                    View.GONE
                adapter.submitData(it)
            }
        }
        return binding.root
    }
}