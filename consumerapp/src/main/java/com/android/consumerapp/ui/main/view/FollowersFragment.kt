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
import com.android.consumerapp.databinding.FragmentFollowersBinding
import com.android.consumerapp.ui.main.adapter.FollowersAdapter


@Suppress("CAST_NEVER_SUCCEEDS")
class FollowersFragment : Fragment() {

    private lateinit var modelViewModel: ModelViewModel
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!
    private lateinit var ctx: Context

    companion object {
        val TAG = FollowersFragment::class.java.simpleName
        const val EXTRA_DATA = "extra_data"
    }

    private var listFollowers: ArrayList<Kontak> = ArrayList()
    private lateinit var adapter: FollowersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
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

        adapter = FollowersAdapter()
        listFollowers.clear()
        adapter.notifyDataSetChanged()
        modelViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(ModelViewModel::class.java)


        showRecycleList()
        getDataGeneral(ctx)
        getList(adapter)


    }

    private fun getList(adapter: FollowersAdapter) {
        modelViewModel.getListKontak().observe(viewLifecycleOwner, { kontak ->
            if (kontak != null) {
                adapter.setData(kontak)
                showLoading(false)
            }

        })
    }

    private fun showRecycleList() {
        binding.recyclerViewKontak.layoutManager = LinearLayoutManager(activity)
        binding.recyclerViewKontak.setHasFixedSize(true)

        binding.recyclerViewKontak.adapter = adapter
    }

    private fun getDataGeneral(context: Context) {
        val user = activity!!.intent.getParcelableExtra<Kontak>(EXTRA_DATA)!!
        modelViewModel.getFollowers(user.username.toString(), context)
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