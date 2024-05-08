package com.example.clicknbuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.clicknbuy.products.FirebaseProductModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

 lateinit var etcustomerName:EditText
 lateinit var etcustomerHomeAddress:EditText
lateinit var btnPlaceOrder:Button

private lateinit var database : DatabaseReference

class PlaceOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_order)

        etcustomerName = findViewById(R.id.etEnterName)
        etcustomerHomeAddress = findViewById(R.id.etEnterHomeAddress)
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder)

        btnPlaceOrder.setOnClickListener {
            val customerName: String = etcustomerName.text.toString()
            val customerHomeAddress: String = etcustomerHomeAddress.text.toString()

            database = FirebaseDatabase.getInstance().getReference("PlaceOrders")
            val product = FirebaseProductModel(customerName, customerHomeAddress)

            database.child(customerName).setValue(product).addOnSuccessListener {
                Toast.makeText(this, "Successfully Saved", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}