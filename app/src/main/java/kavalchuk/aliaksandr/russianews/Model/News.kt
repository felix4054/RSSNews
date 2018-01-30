package kavalchuk.aliaksandr.russianews.Model


/**
 * Created by user on 30.01.2018.
 */


//v2
//data class News(val status: String, val totalResult: Int, val article: List<Article>)

//v1
data class News(val status: String, val source: String, val articles: List<Article>)