package com.example.app1

import android.content.Intent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatDelegate
import com.example.app1.databinding.ActivityLoginPageBinding


class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    var nightMODE = false

    override fun onCreate(savedInstanceState: Bundle? ) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var switcher = binding.switch1
        supportActionBar!!.title = "Welcome"

        var sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE)
        nightMODE = sharedPreferences.getBoolean("night", false)
        if (nightMODE) {
            switcher.setChecked(true)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        switcher.setOnClickListener(View.OnClickListener {
            if (nightMODE) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
               var editor = sharedPreferences.edit()
                editor.putBoolean("night", false)
                editor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               var editor = sharedPreferences.edit()
                editor.putBoolean("night", true)
                editor.apply()
            }
        }
        )
        binding.maps.setOnClickListener{
            var intent = Intent(this,maps::class.java)
            startActivity(intent)
        }
        binding.jimny.setOnClickListener{
            var intent = Intent(this,Model::class.java)
            startActivity(intent)
        }
        binding.button2.setOnClickListener{
            var intent = Intent(this, feedbackform::class.java)
            startActivity(intent)
        }


        }


    }

