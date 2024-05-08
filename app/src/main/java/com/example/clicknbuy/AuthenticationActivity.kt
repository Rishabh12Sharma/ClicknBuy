package com.example.clicknbuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.clicknbuy.products.FirebaseCustomerCredential
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class AuthenticationActivity : AppCompatActivity() {

    var number : String =""

    lateinit var et_name:EditText
    lateinit var et_email_address:EditText
    lateinit var et_phone_number: EditText

    lateinit var auth: FirebaseAuth
    private lateinit var database : DatabaseReference

    lateinit var storedVerificationId:String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        auth=FirebaseAuth.getInstance()

        et_name=findViewById(R.id.et_name)
        et_email_address=findViewById(R.id.et_email_address)
        et_phone_number=findViewById(R.id.et_phone_number)

        findViewById<Button>(R.id.button_otp).setOnClickListener {

            val customerName: String = et_name.text.toString()
            val customerHomeAddress: String = et_email_address.text.toString()
            val customerPhoneNumber:String=et_phone_number.text.toString()

            database = FirebaseDatabase.getInstance().getReference("CustomerCredentials")
            val product = FirebaseCustomerCredential(customerName, customerHomeAddress,customerPhoneNumber)

            database.child(customerName).setValue(product).addOnSuccessListener {
                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }

            login()
        }
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {


            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                Log.d("ClicknBuy" , "onVerificationCompleted Success")
            }


            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("ClicknBuy" , "onVerificationFailed  $e")
            }


            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("ClicknBuy","onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(applicationContext,OtpActivity::class.java)
                intent.putExtra("storedVerificationId",storedVerificationId)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun login() {
        number = findViewById<EditText>(R.id.et_phone_number).text.trim().toString()

        // get the phone number from edit text and append the country cde with it
        if (number.isNotEmpty()){
            number = "+91$number"
            sendVerificationCode(number)
        }else{
            Toast.makeText(this,"Enter mobile number", Toast.LENGTH_SHORT).show()
        }
    }
    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("ClicknBuy" , "Auth started")
    }
}