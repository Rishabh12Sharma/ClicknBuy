package com.example.clicknbuy

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

lateinit var txtProductNameNext: TextView
    lateinit var txtProductAuthorNext: TextView
    lateinit var txtProductPriceNext: TextView
    lateinit var txtProductRatingNext: TextView
    lateinit var imgProductUrl:ImageView
    lateinit var btnbuttonBuy:Button

class NextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        txtProductNameNext=findViewById<TextView>(R.id.textProductName)
        txtProductAuthorNext=findViewById<TextView>(R.id.textProductAuthor)
        txtProductPriceNext=findViewById<TextView>(R.id.textProductPrice)
        imgProductUrl=findViewById<ImageView>(R.id.imgProductImage)
        btnbuttonBuy=findViewById(R.id.buttonBuy)



        txtProductNameNext.text = intent.getStringExtra("ProductName")
        txtProductAuthorNext.text = intent.getStringExtra("ProductType")
        txtProductPriceNext.text = intent.getStringExtra("ProductPrice")
        Glide.with(this)
            .load(intent.getStringExtra("ProductImage"))
            .into(imgProductUrl)




        btnbuttonBuy.setOnClickListener {
            val intent = Intent(this, PlaceOrderActivity::class.java)
            intent.putExtra("ProductName", txtProductNameNext.text)
            intent.putExtra("ProductPrice", txtProductPriceNext.text)
            startActivity(intent)
        }
    }
}