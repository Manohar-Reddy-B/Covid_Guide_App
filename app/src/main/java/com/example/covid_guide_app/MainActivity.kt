package com.example.covid_guide_app


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


lateinit var toggle:ActionBarDrawerToggle

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var databaseRef: DatabaseReference? =null
    private var db: FirebaseDatabase?=null
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArray: ArrayList<daylist>
    private lateinit var imageId: Array<Int>
    private lateinit var heading: Array<String>
    lateinit var content:Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth= FirebaseAuth.getInstance()

        val drawerLayout: DrawerLayout=findViewById(R.id.drawerLayout)
        val navView: NavigationView=findViewById(R.id.nav_view)
        auth= FirebaseAuth.getInstance()
        db= FirebaseDatabase.getInstance()
        databaseRef=db?.reference!!.child("users")
        toggle=ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.logout -> signOut()

            }
            true
        }
        val headerView = navView.getHeaderView(0)
        val usName=headerView.findViewById<TextView>(R.id.usName)
        val usEmail=headerView.findViewById<TextView>(R.id.usEmail)
        val user= auth.currentUser
        val userRef=databaseRef?.child(user?.uid!!)
        usEmail.text=user?.email

        userRef?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                usName.text=snapshot.child("Name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        imageId= arrayOf(
                    R.drawable.yoga,
                    R.drawable.drink,
                    R.drawable.breakfast,
                    R.drawable.music,
                    R.drawable.sp02,
                    R.drawable.fruits,
                    R.drawable.hydration,
                    R.drawable.lunch,
                    R.drawable.call,
                    R.drawable.sp02,
                    R.drawable.hydration,
                    R.drawable.drink,
                    R.drawable.fruits,
                    R.drawable.music,
                    R.drawable.dinner,
                    R.drawable.website,
                    R.drawable.sleep


        )

        heading= arrayOf(
            "Time For YOGA",
            "Time For Drinks",
            "Time For Break Fast",
            "Time For Music",
            "Time For SPO2 Test",
            "Time For Fruits",
            "Time For Hydration",
            "Time For Lunch",
            "Time For CAll",
            "Time For SPO2 Test" ,
            "Time For Hydration",
            "Time For Drinks",
            "Time For Fruits",
            "Time For Music",
            "Time For Dinner",
            "Time For wed site",
            "Time For Sleep",
        )
        content= arrayOf(
            getString(R.string.yoga),
            getString(R.string.call),
            getString(R.string.Website),
            getString(R.string.sleep),
            getString(R.string.dinner)






        )
        newRecyclerView=findViewById(R.id.recycleViews)
        newRecyclerView.layoutManager=LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newArray= arrayListOf<daylist>()
        getUserData()


    }

    private fun getUserData() {
     for(i in imageId.indices){
         val daylist = daylist(imageId[i],heading[i])
         newArray.add(daylist)
     }
        var adapter =MyAdapter(newArray)
        newRecyclerView.adapter=adapter
        adapter.setOnItemClickListener(object:MyAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(this@MainActivity, yogaactivity::class.java)
                intent.putExtra("heading",newArray[position].heading)
                intent.putExtra("imageId",newArray[position].titleImage)
                intent.putExtra("content",content[position])
                startActivity(intent)
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        auth.signOut()
        val intent=Intent(this,login::class.java)
        startActivity(intent)
        finish()
    }
}