package com.example.aptitudequiz


import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.apptitudetest.DoneAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.Math.abs


class Questions : AppCompatActivity() {
    lateinit var thread: Thread

    var timeValue :Int = 60
    var cat = 0

    companion object{
        var allJoined :ArrayList<JoinedFeed> = ArrayList()
        var selectedAnswer :ArrayList<String> = ArrayList()
        var question:Int = 0
        var isCorrect:Int = 0
        var isFailed:Int = 0
        var questionNr:Int = 0


    }

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        cat = intent.extras!!.getInt("category",9)
        val endpoint: String = "https://opentdb.com/api.php?amount=10&type=multiple&category=${cat}";


        val questions: ArrayList<String> = ArrayList();
        val allanswers: ArrayList<ArrayList<String>> = ArrayList();
        val allcorrectanswers: ArrayList<String> = ArrayList();
        val donelayout: ConstraintLayout = findViewById(R.id.done);
        val loading_layout:ConstraintLayout = findViewById(R.id.loading)
        val mainQuestionlayout : LinearLayout = findViewById(R.id.main_question_container)

        donelayout.visibility = View.GONE
        mainQuestionlayout.visibility = View.GONE
        loading_layout.visibility = View.VISIBLE
        thread = object : Thread() {
            override fun run() {
                try {
                    for(i in 1..60){

                        sleep(1000)
                        runOnUiThread {

                           var textView:TextView = findViewById(R.id.timing)
                            timeValue-=1
                            textView.text = "Time Left ${timeValue} Seconds"
                        }
                    }
                } catch (e: InterruptedException) {
                }
            }
        }
        supportActionBar?.hide();
        Toast.makeText(this,"Going in",Toast.LENGTH_SHORT).show()
        val  que = Volley.newRequestQueue(this)
        val string_request = StringRequest(Request.Method.GET,endpoint,Response.Listener <String>{ response ->
           //Toast.makeText(this,"Response"+response.length,Toast.LENGTH_SHORT).show()
            val gsonBuilder= GsonBuilder().create()
            val mainData:allResults = gsonBuilder.fromJson(response,allResults::class.java)
            for((index,value) in mainData.results.withIndex()){

                val mainFeed = mainData.results
                val question = mainFeed[index].question
                questions.add(question)
                val answers = mainFeed[index].incorrect_answers
                answers.add((0..3).random(),mainFeed[index].correct_answer);
                allanswers.add(answers)
                val canswer = mainFeed[index].correct_answer
                allcorrectanswers.add(canswer)



            }
            allJoined.add(JoinedFeed(questions=questions,answer= allanswers,correct_answer = allcorrectanswers))
            loading_layout.visibility = View.GONE
            mainQuestionlayout.visibility = View.VISIBLE

            //Toast.makeText(this,"Response"+response.length,Toast.LENGTH_SHORT).show()


            startQuiz();
            thread.start()


        },
            Response.ErrorListener {


                Toast.makeText(this,"There is an error. Check you Network connection and try again.",Toast.LENGTH_SHORT).show()
                print("That did'nt worked")
            }

        )
        que.add(string_request);


}
    fun startQuiz() {

        Toast.makeText(this,"In the function",Toast.LENGTH_SHORT).show()
        val nextbtn = findViewById<ImageButton>(R.id.next_btn);
        val totalnum: TextView = findViewById<TextView>(R.id.total_num);
        val mainquestion: TextView = findViewById<TextView>(R.id.main_question);
        val donelayout: ConstraintLayout = findViewById(R.id.done);
        val quizlayout: ConstraintLayout = findViewById(R.id.quiz);
        val donepop: ListView = findViewById(R.id.done_pop);

        // Display Number
        var questionNum = questionNr;

        // Get current Question
        val currentQuestion = allJoined[0].questions[questionNr];

        // Grab the listview
        val answerListView: ListView = findViewById(R.id.answers_container);

        // Increase the Display number to +1
        questionNum++;
        totalnum.text = "${questionNum.toString()}/${allJoined[0].questions.count()}";


        // Set the first Question
        mainquestion.text = currentQuestion;

        // Set the first Question Answers
        var qanswers: ArrayList<String> = allJoined[0].answer[questionNr];
        setAnswers(qanswers)

        answerListView.setOnItemClickListener { parent, view, position, id ->
            val clickedID = id.toInt()
            val correctanswer = allJoined[0].correct_answer[questionNr]
            val selectedanswer = allJoined[0].answer[questionNr][clickedID]
            val answerIsCorrect = selectedanswer == correctanswer;

            // Check if answer is correct
            if (answerIsCorrect) {
                isCorrect++
            } else {
                isFailed--
            }

            if(questionNr == allJoined[0].questions.count() -1 && questionNum === 10){

                var test_cat  = ""

                if(cat == 19)
                {
                    test_cat = "Mathematics"
                }
                else if(cat == 18)
                {
                    test_cat = "Computer Science"

                }
                else
                {test_cat = "General Knowledge"}

                val info: DoneFeed = DoneFeed(
                    qNumbers = "${allJoined[0].questions.count()}",
                    qCorrectAnswers = "${isCorrect}",
                    qAttempted = "${10}",
                    qNegative = "${abs(isFailed)}",
                    timeTaken = "${60-timeValue}",
                    test_category = "${test_cat}"

                )
               //var  db = testdbHelper(this,null)
                //db.addName(category = info.test_category,timetaken = info.timeTaken,score = info.qCorrectAnswers,userId = getUser().id)
               // Toast.makeText(this,"Record added",Toast.LENGTH_SHORT).show()
                quizlayout.visibility = View.GONE
                donelayout.visibility= View.VISIBLE

                donepop.adapter = DoneAdapter(this, info)
            }else{
                questionNum++;
                questionNr++
            }

            totalnum.text = "${questionNum.toString()}/${allJoined[0].questions.count()}"
            mainquestion.text = allJoined[0].questions[questionNr];

            //update answers
            val newAnswers = allJoined[0].answer[questionNr];
            setAnswers(newAnswers)
        }
    }

    private fun setAnswers(qanswers: ArrayList<String>) {
        val answers: ListView = findViewById(R.id.answers_container);
        for ((index, value) in qanswers.withIndex()) {
            answers.adapter = AnswerAdapter(this, qanswers)
        }
    }
    fun getUser(): users {

        val sharedPrefFile = "kotlinsharedpreference"
        val sharedPreferences: SharedPreferences = getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)




        val gson = Gson()
        val json: String? = sharedPreferences.getString("current_user", "")
        val obj: users = gson.fromJson(json, users::class.java)
        return obj
    }
 }

