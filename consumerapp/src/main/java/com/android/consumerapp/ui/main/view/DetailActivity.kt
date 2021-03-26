package com.android.consumerapp.ui.main.view



import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.android.consumerapp.model.Kontak
import com.android.consumerapp.R
import com.android.consumerapp.databinding.ActivityDetailBinding
import com.android.consumerapp.model.Favorite
import com.android.consumerapp.ui.main.adapter.DetailAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout


@Suppress("NAME_SHADOWING")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_FAVOR = "extra_favor"
        const val EXTRA_POSITION = "extra_note"
        val TAG: String = DetailActivity::class.java.simpleName
        const val EXTRA_DATA = "extra_data"

    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewPager()
        //Action bar dipersiapkan
        actionBar()

        setDataObject()
        //ubah data
        changeData()


    }

    //mempersiapkan action bar
    private fun actionBar() {
        val actionbar = supportActionBar
        actionbar!!.title = "Detail Kontak"
        actionbar.setDisplayHomeAsUpEnabled(true)
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


    private fun setDataObject() {
        val favUser = intent.getParcelableExtra<Favorite>(EXTRA_FAVOR) as Favorite
        binding.txtUsernameDetail.text = favUser.username.toString()
        binding.txtName.text = favUser.name.toString()
        binding.txtCompany.text = favUser.company.toString()
        binding.txtLoc.text = favUser.location.toString()
        Glide.with(this)
            .load(favUser.avatar)
            .apply(RequestOptions().override(55, 55))
            .into(binding.imgDetail)

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









}
