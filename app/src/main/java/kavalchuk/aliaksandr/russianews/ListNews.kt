package kavalchuk.aliaksandr.russianews

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kavalchuk.aliaksandr.russianews.Adapter.ListNewsAdapter
import kavalchuk.aliaksandr.russianews.Adapter.ListNewsViewHolder
import kavalchuk.aliaksandr.russianews.Adapter.ListSourceViewHolder
import kavalchuk.aliaksandr.russianews.Interface.NewsService
import kavalchuk.aliaksandr.russianews.Model.Article
import kavalchuk.aliaksandr.russianews.Model.News
import kotlinx.android.synthetic.main.activity_list_news.*
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.*

import java.io.IOException


/**
 * Created by user on 29.01.2018.
 */

class ListNews : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private var source = ""
    private var sortBy = ""
    private var webHotURL = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_news)

        rv_list_news.layoutManager = LinearLayoutManager(this)
        rv_list_news.hasFixedSize()

        prepareSwipeRefreshLayout()

        if (intent != null){
            source = intent.getStringExtra(ListSourceViewHolder.SOURCE_ID_KEY)
            sortBy = intent.getStringExtra(ListSourceViewHolder.SORT_BY_KEY)
        }

        if (!source.isEmpty() && !sortBy.isEmpty()) {
            onRefresh()
        }

        dl_list_news.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dl_list_news -> {
                val intent = Intent(applicationContext, DetailActivity::class.java)
                intent.putExtra(ListNewsViewHolder.ARTICLE_LINK_KEY, webHotURL)
                startActivity(intent)
            }
            else -> {
                // else condition
            }
        }
    }


    override fun onRefresh() {
        loadNews(source, false)
        //fetchJSON(source, false)
    }

    private fun prepareSwipeRefreshLayout() {
        srl_list_news.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        srl_list_news.setOnRefreshListener(this)
        srl_list_news.canChildScrollUp()
    }

    private fun fetchJSON(source: String, isRefreshed: Boolean) {

        if (!isRefreshed) {
            val dialog = SpotsDialog(this, R.style.Custom)
            dialog.show()
            //https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=8372d71c2abe4cb4a823c35e608a575a
            val apiUrl = StringBuilder("https://newsapi.org/v1/articles?source=")

            apiUrl.append(source)
                    .append("&sortBy=")
                    .append(sortBy)
                    .append("&apiKey=")
                    .append(NewsService.API_KEY)


            val request = Request.Builder().url(apiUrl.toString()).build()
            val client = OkHttpClient()

            client.newCall(request).enqueue(object : okhttp3.Callback {

                override fun onResponse(call: okhttp3.Call?, response: okhttp3.Response?) {
                    val body = response?.body()?.string()

                    val gson = GsonBuilder().create()
                    val news = gson.fromJson(body, News::class.java)

                    runOnUiThread {
                        rv_list_news.adapter = ListNewsAdapter(news, baseContext)
                        rv_list_news.adapter.notifyDataSetChanged()

                        dialog.dismiss()
                        srl_list_news.isRefreshing = false
                    }
                }

                override fun onFailure(call: okhttp3.Call?, e: IOException?) {
                    println("Failed to request")
                }
            })

        }

    }



    private fun loadNews(source: String, isRefreshed: Boolean) {
        if (!isRefreshed) {

            val dialog = SpotsDialog(this, R.style.Custom)
            dialog.show()

            val apiService = NewsService.create()
            apiService.getNewsArticles(NewsService.getAPIUrl(source, sortBy, NewsService.API_KEY))
                    .enqueue(object : Callback<News> {

                        override fun onResponse(call: Call<News>, response: Response<News>) {

                            dialog.dismiss()

                            val body = response.body()

                            Picasso.with(baseContext).load(body?.articles?.get(0)?.urlToImage)
                                    .into(kbv_top_image_list_news)
                            tv_top_title_list_news.text = body?.articles?.get(0)?.title
                            tv_top_author_list_news.text = body?.articles?.get(0)?.author

                            webHotURL = body?.articles?.get(0)?.url!!

                            runOnUiThread {
                                val removeteFirstItem = body.articles
                                //removeteFirstItem.remove(0)
                                rv_list_news.adapter = ListNewsAdapter(removeteFirstItem, applicationContext)
                                rv_list_news.adapter.notifyDataSetChanged()

                                srl_list_news.isRefreshing = false
                            }
                        }

                        override fun onFailure(call: Call<News>, t: Throwable) {
                            println("Failed to request")
                        }

                    })


        }

    }


}


