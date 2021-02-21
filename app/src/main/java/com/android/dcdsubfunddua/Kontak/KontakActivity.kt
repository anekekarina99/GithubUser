package com.android.dcdsubfunddua.Kontak



import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.dcdsubfunddua.R
import com.android.dcdsubfunddua.databinding.ActivityKontakBinding
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import java.util.ArrayList
import android.widget.Toast
import org.json.JSONArray

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class KontakActivity : AppCompatActivity() {

    val apiKey = "da2ced280645ff5bd924a65350c49fb0b67e5adb"
    val urlFix = "https://api.github.com/"

    val client = AsyncHttpClient()

    companion object {
        private val TAG = KontakActivity::class.java.simpleName
    }
    private lateinit var adapter: KontakAdapter
    private lateinit var binding: ActivityKontakBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKontakBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = KontakAdapter()

        binding.recyclerViewKontak.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewKontak.adapter = adapter

        //menampilkan list recyclerView
        getUsername()
        //menu untuk search
        binding.btnSearch.setOnClickListener {
            adapter.notifyDataSetChanged()
            binding.recyclerViewKontak.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewKontak.adapter = adapter

            val edtSearch = findViewById<EditText>(R.id.edtSearch)
            val nameSearch = edtSearch.text.toString()
            if (nameSearch.isEmpty()) return@setOnClickListener
            showLoading(true)
            setName(nameSearch)
        }
    }

    private fun getUsername() {
        binding.progressBar.visibility = View.VISIBLE
        client.addHeader("Authorization","token ${apiKey}")
        client.addHeader("User-Agent", "request")
        val url = "${urlFix}users"
        client.get(url,object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                val listGetKontak = ArrayList<String>()
                binding.progressBar.visibility = View.INVISIBLE

                val result = responseBody?.let { String(it) }
                Log.d(TAG, "Hasil : ${result.toString()}")
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        listGetKontak.add(username)

                    }


                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(this@KontakActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?,
            ) {
                Log.d("Exception", error?.message.toString())
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }

                Toast.makeText(this@KontakActivity, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }

        })

    }

    //mencocokan nama

    private fun setName(name: String) {
        val listKontak = ArrayList<KontakItems>()

        client.addHeader("Authorization","token ${apiKey}")
        client.addHeader("User-Agent", "request")
        val url ="${urlFix}search/users?q=${name}"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                try {
                    //parsing json
                    val result = responseBody?.let { String(it) }
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("list")

                    for (i in 0 until list.length()) {
                        val kontakSearch = list.getJSONObject(i)
                        val kontakItems = KontakItems()
                        kontakItems.id = kontakSearch.getInt("id").toString()
                        kontakItems.name = kontakSearch.getString("login")
                        if(kontakItems.name != name){
                            val text="Username tidak sesuai"
                            //Toast.makeText(this,text, Toast.LENGTH_LONG).show()
                            finish()
                            startActivity(intent)
                        }
                        kontakItems.avatar = kontakSearch.getString("avatar_url")
                        listKontak.add(kontakItems)

                    }
                    Log.d(TAG, "Sedang di cari")
                    //set data ke adapter
                    adapter.setData(listKontak)
                    showLoading(false)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(this@KontakActivity, e.message, Toast.LENGTH_SHORT)
                        .show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?,
            ) {
                Log.d("onFailure", error?.message.toString())
                Toast.makeText(this@KontakActivity, error?.message, Toast.LENGTH_SHORT)
                    .show()

            }


        })

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        return true
    }

    //showLoading
    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }
}