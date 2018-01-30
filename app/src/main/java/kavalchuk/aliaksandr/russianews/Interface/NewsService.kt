package kavalchuk.aliaksandr.russianews.Interface

import kavalchuk.aliaksandr.russianews.Model.News
import kavalchuk.aliaksandr.russianews.Model.WebSite
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by user on 28.01.2018.
 */

interface NewsService {

//    val apiService = NewsService.create()
//    apiService.getSources()

    @GET("v1/sources?language=en") //v2/sources?apiKey=8372d71c2abe4cb4a823c35e608a575a
    fun getSources(): Call<WebSite>

    @GET() //v2/sources?apiKey=8372d71c2abe4cb4a823c35e608a575a
    fun getNewsArticles(@Url url: String): Call<News>




    /**
     * Companion object to create the SourceService  ?language=en
     */
    companion object Factory {
        val BASE_URL = "https://newsapi.org/"
        val API_KEY = "8372d71c2abe4cb4a823c35e608a575a"


        fun create(): NewsService {
            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(NewsService::class.java)
        }

        fun getAPIUrl(source: String, sortBy: String, apiKEY: String):  String {

            val apiUrl = StringBuilder("https://newsapi.org/v1/articles?source=")

            return apiUrl.append(source)
                    .append("&sortBy=")
                    .append(sortBy)
                    .append("&apiKey=")
                    .append(apiKEY).toString()

        }


    }



}