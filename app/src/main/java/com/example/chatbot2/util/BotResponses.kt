package com.example.chatbot.util

import com.example.chatbot.util.Constants.OPEN_GOOGLE
import com.example.chatbot.util.Constants.OPEN_SEARCH

object BotResponses {

    fun basicResponses(_message:String):String{
        val random = (0..2).random()

        val message = _message.lowercase().replace(" ","")

        return when{

            // hello
            message.contains("hello")->{
                when(random){
                    0->"Helllo There!"
                    1->"Namaste"
                    2->"Bonjour"
                    else -> "Error"
                }
            }

            // How are you?
            message.contains("howareyou") || message.contains("howareyou?")->{
                when(random){
                    0->"I'm fine \nThanks for asking!"
                    1->"Soukya"
                    2->"Pretty Good!\nHow about you?"
                    else -> "Error"
                }
            }

            //open google
            message.contains("open") && message.contains("google")->{
                OPEN_GOOGLE
            }

            //search google
            message.contains("search") ->{
                OPEN_SEARCH
            }

            //get time
            message.contains("time") && message.contains("?") ->{
                Time.timeStamp()
            }

            //flip a coin
            message.contains("flipacoin") -> {
                val coin = (0..1).random()

                val result = if(coin==0)"heads" else "tails"

                "I flip[ed a Coin and it landed on ${result}"
            }



            //solve equation
            message.contains("solve") ->{
                val equation :String = message.substringAfter("solve")
                return try {
                    val answer = SolveMath.solveMath(equation)
                    answer.toString()
                }
                catch(e:Exception){
                    "Sorry i can;t solve this"
                }
            }



            else -> {
                when(random){
                    0->"idk"
                    1->"I don't understand this..."
                    2->"I am not programmed for this!"
                    else -> "Error"
                }
            }
        }

    }



}