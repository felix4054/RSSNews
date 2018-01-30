package kavalchuk.aliaksandr.russianews.Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import kavalchuk.aliaksandr.russianews.DetailActivity
import kavalchuk.aliaksandr.russianews.Model.Article


/**
 * Created by user on 30.01.2018.
 */


class ListNewsViewHolder(internal var rootView: View, var articles: Article? = null) : RecyclerView.ViewHolder(rootView) {

    companion object {
        val ARTICLE_LINK_KEY = "ARTICLE_LINK_KEY"
    }


    init {
        rootView.setOnClickListener {

            val intent = Intent(rootView.context, DetailActivity::class.java)

            intent.putExtra(ARTICLE_LINK_KEY, articles?.url)

            rootView.context.startActivity(intent)
        }
    }

}