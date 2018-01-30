package kavalchuk.aliaksandr.russianews.Model

/**
 * Created by user on 28.01.2018.
 */

data class Source(val id: String, val name: String, val description: String, val url: String,
                  val category: String, val language: String, val country: String,
                  val sortBysAvailable: List<String>)