package com.example.bmicalculator

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private lateinit var sf:SharedPreferences
    private lateinit var editor:SharedPreferences.Editor
    private lateinit var usernameText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        usernameText=findViewById<EditText?>(R.id.username)
        val weightText=findViewById<EditText>(R.id.weight)
        val heightText=findViewById<EditText>(R.id.height)
        val calcButton=findViewById<Button>(R.id.calculate)

        val result_layout=findViewById<View>(R.id.result_layout)
        result_layout.isVisible=false

        calcButton.setOnClickListener {
            val weight=weightText.text.toString()
            val height=heightText.text.toString()

            if(inputValidation(usernameText.text.toString(),weight,height)) {
                val bmi = weight.toFloat() / ((height.toFloat() / 100) * (height.toFloat() / 100))
                // result with 2 decimal place
                val bmi2Digit = String.format("%.2f", bmi).toFloat()
                displayResult(bmi2Digit)
                result_layout.isVisible=true
                // usernameText.setText("")
            }else{
                result_layout.isVisible=false
            }
        }
    }
    private fun displayResult(bmi:Float){
        val resultFloat=findViewById<TextView>(R.id.resultFloat)
        var resultText=findViewById<TextView>(R.id.resultText)
        val resultDescription=findViewById<TextView>(R.id.resultDescription)
        resultFloat.text=bmi.toString()
        // (Normal range: 18.50 - 24.99)

        resultDescription.setText(R.string.description)

        var color=0

        when{
            bmi < 18.5 ->{
                resultText.text="UnderWeight"
                color = R.color.under_weight
            }
            bmi in 18.50..24.99 ->{
                resultText.text="Healthy"
                color=R.color.normal
            }
            bmi in 25.00..29.99->{
                resultText.text="OverWeight"
                color=R.color.over_weight
            }
            bmi > 29.99 ->{
                resultText.text="Obese"
                color=R.color.obese
            }
        }
        resultText.setTextColor(ContextCompat.getColor(this,color))
    }

    private fun inputValidation(username:String?,weight:String?,height:String?):Boolean{
       return when{
           username.isNullOrEmpty()->{
               Toast.makeText(this,"Username field empty",Toast.LENGTH_SHORT).show()
               return false
           }
            weight.isNullOrEmpty()->{
                Toast.makeText(this,"Weight field empty",Toast.LENGTH_SHORT).show()
                return false
            }
           height.isNullOrEmpty()->{
               Toast.makeText(this,"Height field empty",Toast.LENGTH_SHORT).show()
               return false
           }

           else->{
               val tvUsername=findViewById<TextView>(R.id.tvUsername)
               tvUsername.setText(username)
               return true
           }
        }
    }
}