package com.example.covid_guide_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class yogaactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yogaactivity)
        val headings=findViewById<TextView>(R.id.contentheading)
        val content=findViewById<TextView>(R.id.contents)

        val bundle:Bundle?=intent.extras
        val heading =bundle!!.getString("heading")
        val contents= bundle.getString("content")

        headings.text=heading
        content.text=contents



    }
}