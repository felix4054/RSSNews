package kavalchuk.aliaksandr.russianews.Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import kavalchuk.aliaksandr.russianews.ListNews
import kavalchuk.aliaksandr.russianews.Model.Source

/**
 * Created by user on 28.01.2018.
 */

class ListSourceViewHolder(internal var rootView: View, var source: Source? = null) : RecyclerView.ViewHolder(rootView) {

    companion object {
        val SOURCE_TITLE_KEY = "SOURCE_TITLE"
        val SOURCE_ID_KEY = "SOURCE_ID"
        val SORT_BY_KEY = "SORT_BY"
    }


    init {
        rootView.setOnClickListener {

            val intent = Intent(rootView.context, ListNews::class.java)

            //intent.putExtra(SOURCE_TITLE_KEY, source?.name)
            intent.putExtra(SOURCE_ID_KEY, source?.id)
            intent.putExtra(SORT_BY_KEY, source?.sortBysAvailable?.get(0))

            rootView.context.startActivity(intent)
        }
    }

}