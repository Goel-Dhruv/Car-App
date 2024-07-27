package com.example.app1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.app1.databinding.ActivityFeedbackFormBinding
import com.example.app1.databinding.ActivityMainBinding

class feedbackform : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackFormBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFeedbackFormBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var button = binding.button3
        var editTextSubject = binding.subject
        var editTextContent = binding.body
        var editTextToEmail = binding.Feedbackemail
        button.setOnClickListener(View.OnClickListener {
            val subject: String
            val content: String
            val to_email: String
            subject = editTextSubject.getText().toString()
            content = editTextContent.getText().toString()
            to_email = editTextToEmail.getText().toString()
            if (subject == "" && content == "" && to_email == "") {
                Toast.makeText(this@feedbackform, "All fields are required", Toast.LENGTH_SHORT)
                    .show()
            } else {
                sendEmail(subject, content, to_email)
            }
        })
    }

    fun sendEmail(subject: String?, content: String?, to_email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to_email))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, content)
        intent.type = "message/rfc822"
        startActivity(Intent.createChooser(intent, "CHoose email client"))
    }
}