package com.example.covid_guide_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseRef: DatabaseReference? =null
    var db: FirebaseDatabase?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        val etName=findViewById<EditText>(R.id.ET_name)
        val etEmail=findViewById<EditText>(R.id.ET_email)
        val etPass=findViewById<EditText>(R.id.ET_password)
        val bt=findViewById<Button>(R.id.reg_bt)
        val tvLogin=findViewById<TextView>(R.id.tv_login)
        auth= FirebaseAuth.getInstance()
        db= FirebaseDatabase.getInstance()
        databaseRef=db?.reference!!.child("users")

        bt.setOnClickListener {
            if(etName.text.isEmpty()){
                etName.error="Enter Your Name"
            }
            else if(etEmail.text.isEmpty()){
                etEmail.error="Enter Your Email"

            }else if(etPass.text.isEmpty()){
                etPass.error="Enter Your Password"
            }
            else{
                if(etPass.text.trim().length>=8){
                    val date= Calendar.getInstance().get(Calendar.DATE)
                    if (etEmail.text.trim().isNotEmpty() && etPass.text.isNotEmpty()) {
                        createUser(etEmail.text.trim().toString(), etPass.text.trim().toString() , etName.text.toString(),date.toString())

                    }
                }else if (etPass.text.trim().length<8)
                    etPass.error="Length is less than 8"
            }
        }
        tvLogin.setOnClickListener {
            val intent=Intent(this,login::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun createUser(email:String, password:String ,name:String, Date:String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener (this){task ->
                if(task.isSuccessful){
                    val currentUser=auth.currentUser
                    val cuDb=databaseRef?.child((currentUser?.uid!!))
                    cuDb?.child("Name")?.setValue(name)
                    cuDb?.child("Email")?.setValue(email)
                    cuDb?.child("Date")?.setValue(Date)
                    Toast.makeText(this, "LogIn successfully", Toast.LENGTH_SHORT).show()
                    val intent= Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Error! "+task.exception, Toast.LENGTH_LONG).show()
                }
            }
    }

}