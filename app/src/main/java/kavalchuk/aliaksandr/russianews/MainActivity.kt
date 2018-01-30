package kavalchuk.aliaksandr.russianews

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.google.gson.GsonBuilder
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kavalchuk.aliaksandr.russianews.Adapter.ListSourceAdapter
import kavalchuk.aliaksandr.russianews.Interface.NewsService
import kavalchuk.aliaksandr.russianews.Model.WebSite
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_source.layoutManager = LinearLayoutManager(this)
        list_source.hasFixedSize()

        Paper.init(this)

        prepareSwipeRefreshLayout()

        if (!isNetwork(this)) {
            Toast.makeText(this, "No Internet connection", Toast.LENGTH_SHORT).show()
        } else {
            onRefresh()
        }

    }

    override fun onRefresh() {
        //srl_container.isRefreshing = true
        loadWebsiteSource(true)
    }

    private fun isWifiActive(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_WIFI
    }

    private fun isMobileActive(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = cm.activeNetworkInfo
        return info != null && info.type == ConnectivityManager.TYPE_MOBILE
    }

    private fun isNetwork(context: Context): Boolean {
        return isWifiActive(context) || isMobileActive(context)
    }

    private fun prepareSwipeRefreshLayout() {
        srl_main_container.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        srl_main_container.setOnRefreshListener(this)
        srl_main_container.canChildScrollUp()
    }


    private fun loadWebsiteSource(isRefreshed: Boolean) {
        if (!isRefreshed) {

            var cache: String = Paper.book().read("cache")
            if (cache != null && !cache.isEmpty()) {

                val gson = GsonBuilder().create()
                val webSite = gson.fromJson(cache, WebSite::class.java)

                runOnUiThread {
                    list_source.adapter = ListSourceAdapter(webSite, baseContext)
                    list_source.adapter.notifyDataSetChanged()
                }

            } else {

                val dialog = SpotsDialog(this, R.style.Custom)
                dialog.show()

                val apiService = NewsService.create()

                apiService.getSources().enqueue(object : Callback<WebSite> {

                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        println("Failed to request")
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        val body = response.body()

                        runOnUiThread {
                            list_source.adapter = ListSourceAdapter(body!!, baseContext)
                            list_source.adapter.notifyDataSetChanged()
                            // Save cache
                            val gson = GsonBuilder().create()
                            val webSite = gson.toJson(body)
                            Paper.book().write("cache", webSite)

                            dialog.dismiss()
                            srl_main_container.isRefreshing = false
                        }
                    }

                })

            }

        } else {

            val dialog = SpotsDialog(this, R.style.Custom)
            dialog.show()

            val apiService = NewsService.create()

            apiService.getSources().enqueue(object : Callback<WebSite> {

                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    println("Failed to request")
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    val body = response.body()

                    runOnUiThread {
                        list_source.adapter = ListSourceAdapter(body!!, baseContext)
                        list_source.adapter.notifyDataSetChanged()
                        // Save cache
                        val gson = GsonBuilder().create()
                        val webSite = gson.toJson(body)
                        Paper.book().write("cache", webSite)

                        dialog.dismiss()
                        srl_main_container.isRefreshing = false
                    }
                }

            })

        }
    }
}
