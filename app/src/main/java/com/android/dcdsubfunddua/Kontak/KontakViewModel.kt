package com.android.dcdsubfunddua.Kontak


import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class KontakViewModel : ViewModel(){

    //github properties API
    private val apiKey = "2f63000e631da2beebf76ba457b330308f4ddce5"
    private val urlFix = "https://api.github.com/"
    private val client = AsyncHttpClient()

    //Collection /Live Data
    val listKontak = MutableLiveData<ArrayList<Kontak>>()

    //CRUD

    /*getList*/
    fun getListKontak(): LiveData<ArrayList<Kontak>> {
        return listKontak
    }

    /*getData: mengambil data */
    fun getData(context : Context){
       // binding.progressBar.visibility = View.VISIBLE
        client.addHeader("Authorization", apiKey)
        client.addHeader("User-Agent", "request")
        val url = "${urlFix}users"
        client.get(url,object: AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {

                val result = responseBody?.let { String(it) }
                Log.d(KontakActivity.TAG, "Hasil : ${result.toString()}")
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        getDetail(username, context)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(context,"Error getData Catch :$e.message", Toast.LENGTH_SHORT)
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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d(KontakActivity.TAG, errorMessage)
                Toast.makeText(context, "Gagal getData :$errorMessage", Toast.LENGTH_LONG)
                    .show()
            }
        })

    }

    /*searchData = melakukan pencarian*/
    fun searchData(q: String, ctx : Context){
        client.addHeader("Authorization", apiKey)
        client.addHeader("User-Agent", "request")
        val urlSearch = "${urlFix}search/users?q=$q"
        val listKontakNon = ArrayList<Kontak>()
        client.get(urlSearch, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
              val result= responseBody?.let{ String(it) }
                result?.let { Log.d(KontakActivity.TAG, it) }
                try {
                    listKontakNon.clear()
                    val jsonArray = JSONObject()
                    val item = jsonArray.getJSONArray("items")
                    for (index in 0 until item.length()){
                        val jsonObject = item.getJSONObject(index)
                        val username = jsonObject.getString("login")
                        getDetail(username, ctx)
                    }
                }catch (e: Exception){
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(ctx,"Error Search Exception ${e.message}", Toast.LENGTH_LONG)
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
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d(KontakActivity.TAG, errorMessage)
                Toast.makeText(ctx, errorMessage, Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    /*getDetail: mengambil data detail */
    fun getDetail(username: String, context: Context) {
        val listKontakNon = ArrayList<Kontak>()
        client.addHeader("Authorization",apiKey)
        client.addHeader("User-Agent", "request")
        val urlDetail = "${urlFix}users/$username"
        client.get(urlDetail, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
              val result = responseBody?.let{String(it)}
                result?.let { Log.d(KontakActivity.TAG, it) }

                try{
                    val jsonObject = JSONObject(result)
                    val users = Kontak()
                    users.username = jsonObject.getString("login")
                    users.name = jsonObject.getString("name")
                    users.company = jsonObject.getString("avatar_url")
                    users.location = jsonObject.getString("location")
                    users.repository = jsonObject.getString("public_repos")
                    users.followers = jsonObject.getString("followers")
                    users.following = jsonObject.getString("folllowing")

                    listKontakNon.add(users)
                    listKontak.postValue(listKontakNon)
                } catch (e: Exception){
                    Toast.makeText(context, "Error getDetail Exception ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?,
            ) {
                val errorMessage: String = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Log.d(KontakActivity.TAG, errorMessage)
                Toast.makeText(context, "Error Get Detail on failure : ${errorMessage}", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }


}