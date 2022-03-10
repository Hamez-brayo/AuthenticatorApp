package com.example.authenticatorapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class RegisterActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth= FirebaseAuth.getInstance()
        findViewById<Button>(R.id.RegisterButton).setOnClickListener{
            RegisterUser()
        }
    }

    private fun RegisterUser(){
        val name=findViewById<EditText>(R.id.FullName).text.toString()
        val email=findViewById<EditText>(R.id.emailInput).text.toString()
        val password=findViewById<EditText>(R.id.editTextTextPassword).text.toString()
        val ConPass=findViewById<EditText>(R.id.editTextTextPassword2).text.toString()
        if (name.isNotEmpty()&&email.isNotEmpty()&&password.isNotEmpty()&&ConPass.isNotEmpty())
        {
            CoroutineScope(Dispatchers.IO).launch{
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main){
                        checkLoggedInState()
                    }


                }catch (e:Exception){
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@RegisterActivity, "", Toast.LENGTH_LONG).show()
                    }
            }

            }
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser==null){


        }
        else{
            intent=Intent(this@RegisterActivity,LoginActivity::class.java)
            startActivity(intent)

        }
    }

}