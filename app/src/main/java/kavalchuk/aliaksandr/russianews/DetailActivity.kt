package kavalchuk.aliaksandr.russianews

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import dmax.dialog.SpotsDialog
import kavalchuk.aliaksandr.russianews.Adapter.ListNewsViewHolder
import kotlinx.android.synthetic.main.activity_detail_articles.*
import kotlinx.android.synthetic.main.activity_list_news.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_articles)

        val dialog = SpotsDialog(this, R.style.Custom)
        dialog.show()

        val article_link = intent.getStringExtra(ListNewsViewHolder.ARTICLE_LINK_KEY)


        wv_web_detail_article.settings.javaScriptEnabled = true
        wv_web_detail_article.settings.loadWithOverviewMode = true
        wv_web_detail_article.settings.useWideViewPort = true
        wv_web_detail_article.webChromeClient = WebChromeClient()
        wv_web_detail_article.webViewClient = WebViewClient()


        if (intent != null) {

            if (!article_link.isEmpty()) {
                article_link
            }
        }

        wv_web_detail_article.loadUrl(article_link)

    }
}
