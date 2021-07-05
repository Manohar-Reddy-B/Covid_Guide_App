package com.example.covid_guide_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val etEmail=findViewById<EditText>(R.id.ET_email)
        val etPass=findViewById<EditText>(R.id.ET_password)
        val btLogin=findViewById<Button>(R.id.login_bt)
        val tvRegister=findViewById<TextView>(R.id.tv_register)
        auth= FirebaseAuth.getInstance()
        supportActionBar?.hide()

        btLogin.setOnClickListener {
            if(etEmail.text.isEmpty()){
                etEmail.error="Enter Your Email"

            }else if(etPass.text.isEmpty()){
                etPass.error="Enter Your Password"
            }
            else{
                if(etPass.text.trim().length>=8){
                    if (etEmail.text.trim().isNotEmpty() && etPass.text.isNotEmpty()) {
                        loginUser(etEmail.text.trim().toString(), etPass.text.trim().toString())

                    }
                }else if (etPass.text.trim().length<8)
                    etPass.error="Length is less than 8"
            }
        }
        tvRegister.setOnClickListener {
            val intent= Intent(this,Register::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun loginUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener (this){task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "LogIn successfully", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Error! "+task.exception, Toast.LENGTH_LONG).show()
                }
        }
    }
    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}