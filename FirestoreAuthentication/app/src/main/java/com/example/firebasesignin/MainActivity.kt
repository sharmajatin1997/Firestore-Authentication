package com.example.firebasesignin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.firebasesignin.adapter.UserAdapter
import com.example.firebasesignin.databinding.ActivityMainBinding
import com.example.firebasesignin.databinding.ActivitySignInBinding
import com.example.firebasesignin.model.UserModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var userAdapter: UserAdapter
    lateinit var itemsUsers:MutableList<UserModel>

    private var db: FirebaseFirestore? = null
    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this);
        db = FirebaseFirestore.getInstance();

        itemsUsers= arrayListOf()
        initListener()
        initAdapter()
        getuserList()

    }

    private fun initListener(){
        binding.tvLogout.setOnClickListener {
            SignInActivity.start(this)
        }
    }

    private fun initAdapter(){
        userAdapter= UserAdapter(this,itemsUsers)
        binding.rvUsers.adapter=userAdapter
    }

    private fun getuserList(){
        val users = db!!.collection("Users").get().addOnSuccessListener { doc ->
            run {

                for (items in doc.documents) {
                    val model = UserModel()
                    model.name = items.data?.get("name").toString()
                    model.password = items.data?.get("password").toString()
                    model.email = items.data?.get("email").toString()

                    itemsUsers.add(model)
                    userAdapter.notifyDataSetChanged()
                }
            }
        }
    }
}