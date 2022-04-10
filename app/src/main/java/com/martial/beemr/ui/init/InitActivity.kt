package com.martial.beemr.ui.init

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.martialcoder.mathongo.databinding.ActivityInitBinding
import com.martial.beemr.ui.base.MainActivity
import com.martial.beemr.utils.AppPreference

class InitActivity : AppCompatActivity() {
    
    private lateinit var binding : ActivityInitBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkUserEmail()){
            mainActivityIntent()
        }

        binding.continueButton.setOnClickListener{
            val email = binding.userEmail.text
            if(!email.isNullOrEmpty()){
                AppPreference.saveToSharedPrefString(this, AppPreference.USER_EMAIL,email.toString())
                mainActivityIntent()
            }else{
                showToast("Enter your email")
            }
        }
    }
    
    private fun checkUserEmail() : Boolean{
        val userEmail = AppPreference.fetchSharedPrefString(this, AppPreference.USER_EMAIL)
        if(userEmail.isNullOrEmpty()){
            return false
        }
        return true
    }

    private fun mainActivityIntent(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showToast(msg : String){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}