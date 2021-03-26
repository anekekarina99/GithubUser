package com.android.dcdsubfunddua.ui.main.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.ACTION_LOCALE_SETTINGS
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dcdsubfunddua.R
import com.android.dcdsubfunddua.databinding.ActivityKontakBinding
import com.android.dcdsubfunddua.model.Kontak
import com.android.dcdsubfunddua.ui.main.adapter.KontakAdapter
import com.android.dcdsubfunddua.ui.main.viewModel.ModelViewModel



class KontakActivity : AppCompatActivity() {


    private lateinit var modelViewModel: ModelViewModel
    private lateinit var adapterKontak: KontakAdapter
    private lateinit var binding: ActivityKontakBinding

    companion object {
        val TAG: String = KontakActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKontakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //adapter dan perisapan properti
        adapterKontak = KontakAdapter()
        adapterKontak.notifyDataSetChanged()
        modelViewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()).get(ModelViewModel::class.java)

        //Action bar
        val actionbar = supportActionBar
        actionbar!!.title= "Github"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //fungsi fungsi berkaitan


        showRecyclerList()
        getDataGeneral()
        getList(adapterKontak)


    }

    //getData memunculkan data secara detail
    private fun getDataGeneral() {
        modelViewModel.getData(applicationContext)
        showLoading(true)
    }


    //getlist memunculkan list Kontak
    private fun getList(adapter: KontakAdapter) {
        modelViewModel.getListKontak().observe(this, { kontak ->
            if (kontak != null) {
                adapter.setData(kontak)
                showLoading(false)
            }

        })
    }

    //fungsi pencarian2
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)


        /*Search *************************************************************/
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return if (query.isNotEmpty()) {
                    modelViewModel.searchData(query, applicationContext)
                    false
                } else {
                    showLoading(true)
                    true
                }


            }

            override fun onQueryTextChange(newText: String): Boolean {

                showLoading(true)
                return false
            }
        })
        /* End Search *************************************************************/
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                val i = Intent(this, AboutActivity::class.java)
                startActivity(i)
                finish()
                return true
            }
            R.id.menu2 -> {
                val i = Intent(ACTION_LOCALE_SETTINGS)
                startActivity(i)
                return true
            }
            R.id.menuFavorit ->{
                val i = Intent(this, FavorActivity::class.java)
                startActivity(i)
                finish()
                return true
            }
            R.id.menuAlarm ->{
                val i = Intent(this, AlarmActivity::class.java)
                startActivity(i)
                finish()
                return true

            }
            else -> return true
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
        val user = Kontak(
            data.id,
            data.username,
            data.name,
            data.avatar,
            data.company,
            data.repository,
            data.followers,
            data.following
        )
        val i = Intent(this, DetailActivity::class.java)
        i.putExtra(DetailActivity.EXTRA_DATA, user)
        startActivity(i)
        finish()


    }


    //show progressBar
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }

    //back
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
