package com.android.consumerapp.ui.main.view

import android.content.ContentValues
import android.media.tv.TvContract.Channels.CONTENT_URI
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.consumerapp.model.Kontak
import com.android.consumerapp.R
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_AVATAR_URL
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_COMPANY
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_ID
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_LOCATION
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_NAMEF
import com.android.consumerapp.database.FavoriteDatabaseContract.FavoriteColumns.Companion.COLUMN_NAME_USERNAME
import com.android.consumerapp.databinding.ActivityDetailBinding
import com.android.consumerapp.model.Favorite
import com.android.consumerapp.ui.main.adapter.DetailAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout


@Suppress("NAME_SHADOWING")
class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_FAVOR = "extra_favor"
        const val EXTRA_POSITION = "extra_note"
        val TAG: String = DetailActivity::class.java.simpleName
        const val EXTRA_DATA = "extra_data"

    }


    private lateinit var gitHelper: FavoriteHelper
    private var fav: Favorite? = null
    private var isFavor = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //mempersiapkan database
        helperDatabase()

        //Action bar dipersiapkan
        actionBar()

        //Klik favorit
        favoriteButton()

        //ubah data
        changeData()


    }

    //mempersiapkan action bar
    private fun actionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "Detail Kontak"
        actionbar.setDisplayHomeAsUpEnabled(true)
    }

    //mempersiapkan database
    private fun helperDatabase() {
        gitHelper = FavoriteHelper.getInstance(applicationContext)
        gitHelper.open()
    }

    //viewpager liat followers,following,repository
    private fun viewPager() {
        val sectionsPagerAdapter = DetailAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
    }

    //changeData : binding set data di layout
    private fun changeData() {
        val user = intent.getParcelableExtra<Kontak>(EXTRA_DATA)
        if (user != null) {
            binding.txtUsernameDetail.text = user.username
            binding.txtName.text = user.name
            binding.txtCompany.text = user.company
            Glide.with(this)
                .load(user.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imgDetail)
        }

    }

    //back
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    //persiapan favorit button
    private fun favoriteButton() {
        fav = intent.getParcelableExtra(EXTRA_FAVOR)
        if (fav != null) {
            isFavor = true
            val click: Int = R.drawable.ic_baseline_favorite_24
            binding.fab.setImageResource(click)
        } else {
            changeData()
        }

        viewPager()
        binding.fab.setOnClickListener(this)

    }

    //ketika database tak digunakan maka klik onDestroy
    override fun onDestroy() {
        super.onDestroy()
        gitHelper.close()
    }

    //on click jika ingin menambahkan sebagai favorit dan merupakan interface dari onView callback
    override fun onClick(view: View) {

        val click: Int = R.drawable.ic_baseline_favorite_24
        val unClick: Int = R.drawable.ic_baseline_favorite_border_24
        if (view.id == R.id.fab) {
            if (isFavor) {
                gitHelper.deleteById(fav?.userNameF.toString())

                Toast.makeText(applicationContext,
                    "Anda tidak sebagai user favorite",
                    Toast.LENGTH_SHORT).show()
                binding.fab.setImageResource(click)
                isFavor = false
            } else {
                val favData = intent.getParcelableExtra<Favorite>(EXTRA_FAVOR) as Favorite
                val dataName = favData.nameF.toString()
                val dataUsername = favData.userNameF.toString()
                val dataAvatar = favData.avatarF.toString()
                val dataCompany = favData.companyF.toString()
                val dataLocation = favData.locationF.toString()
                val dataId = favData.id


                val value = ContentValues()
                value.put(COLUMN_NAME_NAMEF, dataName)
                value.put(COLUMN_NAME_USERNAME, dataUsername)
                value.put(COLUMN_NAME_AVATAR_URL, dataAvatar)
                value.put(COLUMN_NAME_COMPANY, dataCompany)
                value.put(COLUMN_NAME_LOCATION, dataLocation)
                value.put(COLUMN_NAME_ID, dataId)

                isFavor = true
                contentResolver.insert(CONTENT_URI, value)
                Toast.makeText(applicationContext,
                    "Anda menambah sebagai user favorite",
                    Toast.LENGTH_SHORT).show()
                binding.fab.setImageResource(unClick)
            }
        }

    }

}
