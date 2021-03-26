package com.android.dcdsubfunddua.ui.main.viewModel



import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.dcdsubfunddua.model.Kontak
import com.android.dcdsubfunddua.ui.main.view.DetailActivity
import com.android.dcdsubfunddua.ui.main.view.FollowersFragment
import com.android.dcdsubfunddua.ui.main.view.FollowingFragment
import com.android.dcdsubfunddua.ui.main.view.KontakActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class ModelViewModel : ViewModel() {
    //github properties API
    val client = AsyncHttpClient()
    private val apiKey = "2f8751cae854ab166c0414cba7a48cda67ef1b23"
    private val urlFix = "https://api.github.com/"


    //Collection /Live Data
    val listKontak = MutableLiveData<ArrayList<Kontak>>()
    val listKontakNon = ArrayList<Kontak>()

    //CRUD

    /*getList*/
    fun getListKontak(): LiveData<ArrayList<Kontak>> {
        return listKontak
    }




    /*getData: mengambil data */
    fun getData(context: Context) {
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        val url = "https://api.github.com/users"
        client.get(url, object : AsyncHttpResponseHandler() {
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
                    Toast.makeText(context, "Error getData Catch :$e.message", Toast.LENGTH_SHORT)
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

    /*getDataRepos: mengambil data
    fun getDataRepos(context: Context) {
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        val url = "https://api.github.com/users"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {

                val result = responseBody?.let { String(it) }
                Log.d(ReposFragment.TAG, "Hasil : ${result.toString()}")
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        getRepos(username, context)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(context, "Error getDataRepos Catch :$e.message", Toast.LENGTH_SHORT)
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
                Toast.makeText(context, "Gagal getDataRepos :$errorMessage", Toast.LENGTH_LONG)
                    .show()
            }
        })

    }

    //melakukan pemanggilan Repos

    fun getRepos(q: String,ctx:Context){

        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")

        val urlDetail = "https://api.github.com/users/$q/repos"
        client.get(urlDetail, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                val result = responseBody?.let { String(it) }
                result?.let { Log.d(DetailActivity.TAG, it) }

                try {
                    val jsonObject = JSONObject(result)
                    val repos = Repos()
                    repos.id = jsonObject.getString("id")
                    repos.nameRepos = jsonObject.getString("name")
                    repos.nameFull = jsonObject.getString("full_name")

                    listReposNon.add(repos)
                    listRepos.postValue(listReposNon)
                } catch (e: Exception) {
                    Toast.makeText(ctx,
                        "Error getRepos Exception ${e.message}",
                        Toast.LENGTH_SHORT).show()
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
                Toast.makeText(ctx,
                    "Error Get Detail on failure : $errorMessage",
                    Toast.LENGTH_LONG)
                    .show()
            }

        })

    } */

    //melakukan pemanggilan data following

    fun getFollowing(q: String, ctx: Context) {
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        val url = "https://api.github.com/users/$q/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowingFragment.TAG, "Hasil : ${result.toString()}")
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetail(username, ctx)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(ctx, "Error getFollowing Catch :$e.message", Toast.LENGTH_SHORT)
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

                Log.d(DetailActivity.TAG, errorMessage)
                Toast.makeText(ctx, "Gagal mengambil following : $errorMessage", Toast.LENGTH_LONG)
                    .show()
            }

        })

    }
    //melakukan pemanggilan data followera

    fun getFollowers(q: String, ctx: Context) {
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")
        val url = "https://api.github.com/users/$q/followers"
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                val result = responseBody?.let { String(it) }
                Log.d(FollowersFragment.TAG, "Hasil : ${result.toString()}")
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val username: String = jsonObject.getString("login")
                        getDetail(username, ctx)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(ctx, "Error getFollowers Catch :$e.message", Toast.LENGTH_SHORT)
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

                Log.d(DetailActivity.TAG, errorMessage)
                Toast.makeText(ctx, "Gagal mengambil followers : $errorMessage", Toast.LENGTH_LONG)
                    .show()
            }

        })

    }

    /*searchData = melakukan pencarian*/
    fun searchData(q: String, ctx: Context) {

        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")

        val urlSearch = "${urlFix}search/users?q=$q"

        client.get(urlSearch, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                val result = responseBody?.let { String(it) }
                result?.let { Log.d(KontakActivity.TAG, it) }
                try {
                    listKontakNon.clear()
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (index in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(index)
                        val username = jsonObject.getString("login")
                        getDetail(username, ctx)
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                    Toast.makeText(ctx, "Error Search Exception ${e.message}", Toast.LENGTH_LONG)
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
        client.addHeader("User-Agent", "request")
        client.addHeader("Authorization", "token $apiKey")

        val urlDetail = "${urlFix}users/$username"
        client.get(urlDetail, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
            ) {
                val result = responseBody?.let { String(it) }
                result?.let { Log.d(KontakActivity.TAG, it) }

                try {
                    val jsonObject = JSONObject(result)
                    val users = Kontak()
                    users.id = jsonObject.getString("id")
                    users.username = jsonObject.getString("login")
                    users.name = jsonObject.getString("name")
                    users.avatar = jsonObject.getString("avatar_url")
                    users.company = jsonObject.getString("organizations_url")
                    users.repository = jsonObject.getString("public_repos")
                    users.followers = jsonObject.getString("followers_url")
                    users.following = jsonObject.getString("following_url")

                    listKontakNon.add(users)
                    listKontak.postValue(listKontakNon)
                } catch (e: Exception) {
                    Toast.makeText(context,
                        "Error getDetail Exception ${e.message}",
                        Toast.LENGTH_SHORT).show()
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
                Toast.makeText(context,
                    "Error Get Detail on failure : $errorMessage",
                    Toast.LENGTH_LONG)
                    .show()
            }

        })
    }


}