package com.example.app1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.app1.databinding.ActivityRegistrationPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationPage : AppCompatActivity() {
    private lateinit var binding:ActivityRegistrationPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistrationPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        db= FirebaseFirestore.getInstance()
        binding.Continue.setOnClickListener {
            if(checking()){
                var email=binding.EmailRegister.text.toString()
                var password =binding.PasswordRegister.text.toString()
                var name=binding.Name.text.toString()
                var phone=binding.Phone.text.toString()
                var confirmpassword = binding.confirmPassword.toString()
                val user = hashMapOf(
                    "Name" to name,
                    "Phone" to phone,
                    "Email" to email,
                )
                val users = db.collection("USERS")
                val query= users.whereEqualTo("Email",email).get().addOnSuccessListener {
                            it->
                        if(it.isEmpty){
                            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                                        task->
                                    if(task.isSuccessful)
                                    {
                                        users.document(email).set(user)
                                        val intent= Intent(this,MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {Toast.makeText(this,"Authentication Failed", Toast.LENGTH_LONG).show()}
                                }

                        }
                        else {
                            Toast.makeText(this,"User Already Registered", Toast.LENGTH_LONG).show()
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent)
                        }
                    }

            }

            else{ Toast.makeText(this,"Enter the  Correct Details", Toast.LENGTH_LONG).show()}
        }

    }
    private fun checking():Boolean{
        if(binding.Name.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.Phone.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.EmailRegister.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.PasswordRegister.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.confirmPassword.text.toString().trim{it<=' '}.isNotEmpty()
            && binding.confirmPassword.text.toString() == binding.PasswordRegister.text.toString()
        ) {
            return true
        }
        return false
    }
}