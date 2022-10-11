package com.example.firebasesignin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasesignin.databinding.ActivitySignInBinding
import com.example.firebasesignin.model.UserModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignInBinding
    var db: FirebaseFirestore? = null
    val TAG = "FIRESTORE"


    lateinit var itemsUser: MutableList<UserModel>

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignInActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()
        itemsUser = arrayListOf()
        initListener()
        getAllUsers()
    }

    private fun initListener() {

        binding.layoutSignIn.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        binding.tvSignIn.setOnClickListener {
            if (itemsUser.isEmpty()) {
                Toast.makeText(this, "You dont have account", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (binding.etEmail.text.isNullOrEmpty()) {
                binding.etEmail.setError("Please enter your email")
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }

            if (binding.etPassword.text.isNullOrEmpty()) {
                binding.etPassword.setError("Please enter your password")
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            for (item in itemsUser) {
                if (item.email != binding.etEmail.text.toString() && item.name != binding.etPassword.text.toString()) {
                    Toast.makeText(this, "You don't have account", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(this, "Successfully login", Toast.LENGTH_SHORT).show()
                    MainActivity.start(this)
                }
            }

        }

        binding.tvSignUp.setOnClickListener {
            SignUpActivity.start(this)
            finish()
        }
    }

    private fun getAllUsers() {
        db!!.collection("Users").get().addOnSuccessListener { doc ->
            run {

                for (items in doc.documents) {
                    val model = UserModel()
                    model.name = items.data?.get("name").toString()
                    model.password = items.data?.get("password").toString()
                    model.email = items.data?.get("email").toString()
                    itemsUser.add(model)
                }
            }
        }
    }

}