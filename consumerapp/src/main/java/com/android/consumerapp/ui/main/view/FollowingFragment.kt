package com.android.consumerapp.ui.main.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.consumerapp.model.Kontak
import com.android.consumerapp.ui.main.viewModel.ModelViewModel
import com.android.consumerapp.databinding.FragmentFollowingBinding
import com.android.consumerapp.ui.main.adapter.FollowingAdapter



@Suppress("CAST_NEVER_SUCCEEDS")
class FollowingFragment : Fragment() {
    private lateinit var modelViewModel: ModelViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!
    private lateinit var ctx: Context

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
    }

    private var listFollowing: ArrayList<Kontak> = ArrayList()
    private lateinit var adapter: FollowingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ctx = context!!

        adapter = FollowingAdapter()
        listFollowing.clear()
        adapter.notifyDataSetChanged()
        modelViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(ModelViewModel::class.java)


        showRecycleList()
        getDataGeneral(ctx)
        getList(adapter)


    }

    private fun getList(adapter: FollowingAdapter) {
        modelViewModel.getListKontak().observe(viewLifecycleOwner, { kontak ->
            if (kontak != null) {
                adapter.setData(kontak)
                showLoading(false)
            }

        })
    }

    private fun showRecycleList() {
        binding.recyclerViewFollowing.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewFollowing.setHasFixedSize(true)

        binding.recyclerViewFollowing.adapter = adapter
    }

    private fun getDataGeneral(context: Context) {
        val user = activity!!.intent.getParcelableExtra<Kontak>(EXTRA_DATA)!!
        modelViewModel.getFollowing(user.username.toString(), context)
        showLoading(true)
    }


    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}