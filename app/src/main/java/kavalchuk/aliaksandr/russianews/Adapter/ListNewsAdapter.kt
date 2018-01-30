package kavalchuk.aliaksandr.russianews.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kavalchuk.aliaksandr.russianews.Model.News
import kavalchuk.aliaksandr.russianews.R
import kavalchuk.aliaksandr.russianews.Utils.ISO8601DateParser
import kotlinx.android.synthetic.main.news_list.view.*
import java.util.*


/**
 * Created by user on 30.01.2018.
 */

class ListNewsAdapter(private val news: News, context: Context) : RecyclerView.Adapter<ListNewsViewHolder>() {

    override fun getItemCount(): Int {
        return news.articles.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListNewsViewHolder? {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val newsListRow = layoutInflater.inflate(R.layout.news_list, parent, false)

        return ListNewsViewHolder(newsListRow)

    }

    override fun onBindViewHolder(holder: ListNewsViewHolder?, position: Int) {

        val articles = news.articles[position]


        val thumbnailImageView = holder?.rootView?.cim_news_image
        Picasso.with(holder?.rootView?.context).load(articles.urlToImage).into(thumbnailImageView)


        if (articles.title.length > 65) {
            holder?.rootView?.tv_article_title?.text = articles.title.substring(0, 65) + "..."
        } else {
            holder?.rootView?.tv_article_title?.text = articles.title
        }

        var date: Date? = null
        try {
            date = ISO8601DateParser.parse(articles.publishedAt)

        } catch (ex:Exception) {
            ex.localizedMessage
            println(" Error Date ISO8610")
        }

        holder?.rootView?.tv_article_time?.setReferenceTime(date!!.time)

        holder?.articles = articles


    }

}