package com.example.aptitudequiz

class StatFeed (val name:String, val image:Int){

}
data class User(val id: Int = -1, val name: String, val email: String, val password: String)

class allResults(val results:List<ResultFeed>)
class QuestionResults(val results:String)

class ResultFeed(
    val category:String,
    val type:String,
    val difficulty:String,
    val question:String,
    val correct_answer:String,
    val incorrect_answers:ArrayList<String>


)
class JoinedFeed(
    val questions:ArrayList<String>,
    val answer:ArrayList<ArrayList<String>>,
    val correct_answer:ArrayList<String>
)
class DoneFeed(
    val qNumbers: String,
    val qCorrectAnswers: String,
    val qAttempted: String,
    val qNegative: String,
    val timeTaken:String,
    val test_category:String

)

