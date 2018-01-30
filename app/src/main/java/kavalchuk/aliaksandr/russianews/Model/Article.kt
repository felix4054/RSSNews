package kavalchuk.aliaksandr.russianews.Model




/**
 * Created by user on 30.01.2018.
 */


//v2
//data class Article(val source: Source, val title: String, val description: String,
//                   val url: String, val urlToImage: String, val publishedAt:  String)

//v1
data class Article(val author: String, val title: String, val description: String,
                   val url: String, val urlToImage: String, val publishedAt: String)
