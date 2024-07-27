package com.example.app1


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.app1.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        val gso= GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient= GoogleSignIn.getClient(this,gso)
        binding.googleButton.setOnClickListener{
            signInGoogle()
        }
        binding.Register.setOnClickListener { var intent = Intent(this,RegistrationPage::class.java)
            startActivity(intent) }
        binding.Login.setOnClickListener {
            if(checking()){
                var email= binding.Email.text.toString()
                var password = binding.Password.text.toString()
                auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) {
                            task->if(task.isSuccessful){
                        var intent = Intent(this,LoginPage::class.java)
                        startActivity(intent)
                    }
                    else{
                        Toast.makeText(this,"Wrong Details",Toast.LENGTH_LONG).show()
                    }
                    }
            }
            else{
                Toast.makeText(this,"Enter the Details",Toast.LENGTH_LONG).show()
            }
        }

    }
    private fun checking():Boolean{
        if (binding.Email.text.toString().trim{it<=' '}.isNotEmpty()&&binding.Password.text.toString().trim{it<=' '}.isNotEmpty()){
            return true
        }
        return false
    }
    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if (result.resultCode== Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handeResult(task)
        }
    }

    private fun handeResult(task: Task<GoogleSignInAccount>) {
       if(task.isSuccessful){
           val account : GoogleSignInAccount? = task.result
           if (account!=null){
               updateUI(account)
           }

       }else{
           Toast.makeText(this,task.exception.toString(),Toast.LENGTH_SHORT).show()
       }
    }

    private fun updateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if(it.isSuccessful){
                val intent:Intent= Intent(this,LoginPage::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
            }
        }

    }


}
