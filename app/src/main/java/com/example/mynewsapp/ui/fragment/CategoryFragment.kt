package com.example.mynewsapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mynewsapp.R
import com.example.mynewsapp.databinding.CategorySelectBinding
import com.example.mynewsapp.ui.adapter.CategoryRecyclerAdapter
import com.example.mynewsapp.ui.model.CategoryItemModel
import com.example.mynewsapp.di.datastore.DataStoreViewModel
import com.example.mynewsapp.ui.adapter.OnItemClickListener
import com.example.mynewsapp.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoryFragment: Fragment() {
    private val dsViewModel:DataStoreViewModel by viewModels()
    private var _binding:CategorySelectBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CategorySelectBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = binding.categoryRv
        val titleList = resources.getStringArray(R.array.category)
        val imageList = listOf(
            R.drawable.business,
            R.drawable.entertainment,
            R.drawable.general,
            R.drawable.health,
            R.drawable.science,
            R.drawable.sports,
            R.drawable.technology
        )
        val itemList :List<CategoryItemModel> = imageList.zip(titleList){ image ,title ->
            CategoryItemModel(image,title)
        }
        val adapter = CategoryRecyclerAdapter(itemList,requireContext(),object:
            OnItemClickListener {
            override fun onItemClick(v: View, position: Int) {
                lifecycleScope.launch {
                    dsViewModel.setCategory(itemList[position].title).collect()
                    (activity as? MainActivity)?.bottomNavigationView?.selectedItemId = R.id.menu_home
                    parentFragmentManager.beginTransaction().replace(R.id.contentFrame,HomeFragment()).commit()
                }
            }
        })
        rv.adapter = adapter
        rv.layoutManager = GridLayoutManager(requireContext(),2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}