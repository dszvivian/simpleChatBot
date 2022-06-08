package com.example.chatbot2.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.util.BotResponses
import com.example.chatbot.util.Constants
import com.example.chatbot.util.Time
import com.example.chatbot2.R
import com.example.chatbot2.data.Message
import com.example.chatbot2.util.MessagingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {



    private lateinit var adapter: MessagingAdapter
    private var botList = listOf("Potter","Frankeinstein","Igor","Parker")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView()

        clickEvents()

        val bot = (0..3).random()
        customMessage("Hello\nToday you are speaking with ${botList[bot]},\nHow are you Doing ?")



    }




    private fun clickEvents() {
        btn_send.setOnClickListener{
            sendMessage()
        }

        et_message.setOnClickListener{
            GlobalScope.launch {
                delay(100)
                withContext(Dispatchers.Main){
                    rv_messages.scrollToPosition(adapter.itemCount -1)
                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        rv_messages.adapter = adapter
        rv_messages.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun sendMessage(){
        val message = et_message.text.toString()
        val timeStamp = Time.timeStamp()

        if(message.isNotEmpty()){
//            messagesList.add(Message(message,SEND_ID,timeStamp))
            et_message.setText("")

            adapter.insertMessage(Message(message, Constants.SEND_ID,timeStamp))
            rv_messages.scrollToPosition(adapter.itemCount-1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            delay(1000)

            withContext(Dispatchers.Main){
                val response = BotResponses.basicResponses(message)

//                messagesList.add(Message(response, RECIEVE_ID,timeStamp ))

                adapter.insertMessage(Message(response, Constants.RECIEVE_ID,timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount -1)


                when(response){
                    Constants.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constants.OPEN_SEARCH ->{
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm : String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?q=$searchTerm")
                        startActivity(site)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        GlobalScope.launch {
            withContext(Dispatchers.Main){
                rv_messages.scrollToPosition(adapter.itemCount -1)
            }
        }
    }


    private fun customMessage(message: String) {
        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main){
                val timeStamp = Time.timeStamp()
                adapter.insertMessage(Message(message, Constants.RECIEVE_ID,timeStamp))
                rv_messages.scrollToPosition(adapter.itemCount -1)
            }
        }
    }

}