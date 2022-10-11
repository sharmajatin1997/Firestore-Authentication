package com.example.firebasesignin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.example.firebasesignin.databinding.ActivitySignInBinding
import com.example.firebasesignin.databinding.ActivitySignUpBinding
import com.example.firebasesignin.firebase.Course
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding
    var db: FirebaseFirestore? = null


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)
        db=FirebaseFirestore.getInstance()
        initListener()

    }

    private fun initListener(){
        binding.layoutSignUp.setOnClickListener {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }

        binding.tvSignUp.setOnClickListener {
            if (binding.etName.text.isNullOrEmpty()) {
                binding.etName.error = "Please enter your name"

                binding.etName.requestFocus()
                return@setOnClickListener
            }

            if (binding.etEmail.text.isNullOrEmpty()) {
                binding.etEmail.error = "Please enter your email"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (binding.etPassword.text.isNullOrEmpty()) {
                binding.etPassword.error = "Please enter your password"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }
            addDataToFirestore(binding.etName.text.toString(),binding.etEmail.text.toString(),binding.etPassword.text.toString())
            MainActivity.start(this)
        }
        binding.tvSignIn.setOnClickListener {
            SignInActivity.start(this)
            finish()
        }
    }


    private fun addDataToFirestore(name:String,
        email: String,
        password: String,

        ) {
        val dbCourses = db!!.collection("Users")
        val courses = Course(name,email, password)

        dbCourses.add(courses).addOnSuccessListener {
            Toast.makeText(this,
                "Your account has been added to Firebase Firestore",
                Toast.LENGTH_SHORT).show()
        }
            .addOnFailureListener { e ->

                Toast.makeText(this, "Fail to add account \n$e", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnCanceledListener {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT)
                    .show()
            }
    }
}