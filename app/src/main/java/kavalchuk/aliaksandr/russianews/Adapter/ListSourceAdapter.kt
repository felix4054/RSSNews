package kavalchuk.aliaksandr.russianews.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kavalchuk.aliaksandr.russianews.Model.WebSite
import kavalchuk.aliaksandr.russianews.R
import kotlinx.android.synthetic.main.cource_list.view.*
import kotlinx.android.synthetic.main.news_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by user on 28.01.2018.
 */

class ListSourceAdapter(private val webSite: WebSite, context: Context) : RecyclerView.Adapter<ListSourceViewHolder>() {

    val context = context

    override fun getItemCount(): Int {
        return webSite.sources.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ListSourceViewHolder? {

        val layoutInflater = LayoutInflater.from(parent?.context)
        val sourceListRow = layoutInflater.inflate(R.layout.cource_list, parent, false)

        return ListSourceViewHolder(sourceListRow)

    }

    override fun onBindViewHolder(holder: ListSourceViewHolder?, position: Int) {

        val source = webSite.sources[position]

        holder?.rootView?.tv_source_name?.text = source.name

        if (source.description.length > 100) {
            holder?.rootView?.tv_source_desc?.text = source.description.substring(0, 100) + "..."
        } else {
            holder?.rootView?.tv_source_desc?.text = source.description
        }

        holder?.source = source

    }

}




