package com.example.clicknbuy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.clicknbuy.products.Product
import com.example.clicknbuy.adapter.DashboardRecyclerAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductActivity : AppCompatActivity() {
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var recyclerAdapter: DashboardRecyclerAdapter

    // List to hold the retrieved data
    private val productList = ArrayList<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        recyclerDashboard = findViewById(R.id.recyclerDashboard)
        layoutManager = LinearLayoutManager(this@ProductActivity)

        recyclerAdapter = DashboardRecyclerAdapter(this@ProductActivity, productList)
        recyclerDashboard.adapter = recyclerAdapter
        recyclerDashboard.layoutManager = layoutManager

        recyclerDashboard.addItemDecoration(
            DividerItemDecoration(
                recyclerDashboard.context,
                (layoutManager as LinearLayoutManager).orientation
            )
        )

        val database = FirebaseDatabase.getInstance()
        val databaseReference = database.reference

// Reference to the location of the data you want to read
        val dataReference = databaseReference.child("Products")

// Attach a ValueEventListener to listen for changes in the data
        dataReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Handle data changes

                productList.clear()

                for (snapshot in dataSnapshot.children) {
                    val productName = snapshot.child("ProductName").getValue(String::class.java)
                    val productAuthor = snapshot.child("ProductAuthor").getValue(String::class.java)

                    // Retrieve ProductPrice and ProductRating as Long or Double depending on the data type
                    val productPrice = snapshot.child("ProductPrice").getValue(Long::class.java)
                    val productRating = snapshot.child("ProductRating").getValue(Double::class.java)

                    val productImage = snapshot.child("ProductImage").getValue(String::class.java)

                    // Check if ProductPrice and ProductRating are not null before proceeding
                    if (productName != null && productAuthor != null && productPrice != null && productRating != null ) {
                        // Convert Long and Double values to strings
                        val priceString = productPrice.toString()
                        val ratingString = productRating.toString()
                        Log.d("ProductName", "Product Name")
                        // Create the Product object
                        val product = Product(productName, productAuthor, priceString, ratingString,productImage)
                        productList.add(product)
                    }

                }
                recyclerAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors
            }
        })
    }
}