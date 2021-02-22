package com.android.dcdsubfunddua.Kontak

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dcdsubfunddua.R
import com.android.dcdsubfunddua.databinding.ActivityKontakBinding
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class KontakActivity : AppCompatActivity() {


    private lateinit var kontakViewModel: KontakViewModel
    private lateinit var adapterKontak: KontakAdapter
    private lateinit var binding: ActivityKontakBinding

    companion object {
        val TAG = KontakActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKontakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapterKontak = KontakAdapter()
        adapterKontak.notifyDataSetChanged()
        kontakViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(KontakViewModel::class.java)

        //fungsi fungsi berkaitan

        showRecyclerList()
        getData()
        getList(adapterKontak)
        searchGeneral()

    }

    //getData memunculkan data secara detail
    private fun getData(){
        kontakViewModel.getData(applicationContext)
        showLoading(true)
    }


    //getlist memunculkan list Kontak
    private fun getList(adapter: KontakAdapter) {
        kontakViewModel.getListKontak().observe(this, { kontak ->
            if (kontak != null) {
                adapterKontak.setData(kontak)
                showLoading(false)
            }

        })
    }

    //fungsi pencarian
    private fun searchGeneral() {
        binding.btnSearch.setOnClickListener {
            val edtSearch = findViewById<EditText>(R.id.edtSearch)
            val nameSearch = edtSearch.text.toString()

            if (nameSearch.isEmpty()) return@setOnClickListener
            showLoading(true)
            kontakViewModel.searchData(nameSearch, applicationContext)


        }
    }

    //show RecyclerView
    private fun showRecyclerList() {
        binding.recyclerViewKontak.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewKontak.setHasFixedSize(true)

        binding.recyclerViewKontak.adapter = adapterKontak

        adapterKontak.setOnItemClickCallback(object : KontakAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Kontak) {
                showSelectedKontak(data)
            }
        })

    }

    //memilih kontak
    private fun showSelectedKontak(data: Kontak) {
        Toast.makeText(this, "Kamu memilih ${data.name}", Toast.LENGTH_SHORT).show()
    }


    //show progressBar
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }
}
