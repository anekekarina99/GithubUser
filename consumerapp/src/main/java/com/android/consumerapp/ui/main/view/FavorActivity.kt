package com.android.consumerapp.ui.main.view

import android.database.ContentObserver
import android.os.Bundle
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.consumerapp.databinding.ActivityFavorBinding
import com.android.consumerapp.helper.MapHelper
import com.android.consumerapp.model.Favorite
import com.android.consumerapp.ui.main.adapter.FavoriteAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavorActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_STATE = "EXTRA_STATE"
    }

    private lateinit var favorAdapter: FavoriteAdapter
    private lateinit var binding: ActivityFavorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //menset action bar
        setActionBarTitle()

        //menyiapkan data /list favorite
        recyclerViewSet()

        //handle thread
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val favObsever = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                loadDataAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, favObsever)

        if (savedInstanceState == null) {
            loadDataAsync()
        } else {
            val listFavor = savedInstanceState.getParcelableArrayList<Favorite>(EXTRA_STATE)
            if (listFavor != null) {
                favorAdapter.listFavorite = listFavor
            }
        }

    }
    //end handle tread

    //meload data favorite dari SQL database
    private fun loadDataAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressBar.visibility = View.INVISIBLE

            val deffFavor = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null, null, null, null)
                //mapping helper
                MapHelper.mapCursorToArrayList(cursor)
            }

            val favData = deffFavor.await()
            binding.progressBar.visibility = View.VISIBLE

            if (favData.size > 0) {
                favorAdapter.listFavorite = favData


            } else {
                favorAdapter.listFavorite = ArrayList()
                Snackbar.make(binding.recyclerViewFavor,
                    "Maaf data tidak ada",
                    Snackbar.LENGTH_SHORT).show()
            }

        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, favorAdapter.listFavorite)
    }

    //set Toolbar
    private fun setActionBarTitle() {
        if (supportActionBar != null) {
            supportActionBar?.title = "Favourite Users"
        }
    }

    //fungsi menyiapkan data /list favorite
    private fun recyclerViewSet() {
        binding.recyclerViewFavor.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewFavor.setHasFixedSize(true)
        favorAdapter = FavoriteAdapter(this)
        binding.recyclerViewFavor.adapter = favorAdapter
    }

    //merefresh data
    override fun onResume() {
        super.onResume()
        loadDataAsync()
    }


}
