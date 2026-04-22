package com.example.functions

import android.os.Bundle
import android.widget.ScrollView
import android.widget.TextView
import androidx.activity.ComponentActivity

data class Product(
    val name: String,
    val price: Double,
    val category: String,
    val inStock: Boolean
)

typealias DiscountRule = (Double) -> Double

fun applyDiscount(product: Product, rule: DiscountRule): Product {
    return product.copy(price = rule(product.price))
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ScrollView for long output
        val scrollView = ScrollView(this)
        val textView = TextView(this)

        textView.textSize = 16f
        textView.setPadding(32, 32, 32, 32)

        scrollView.addView(textView)
        setContentView(scrollView)

        val products = listOf(
            Product("Laptop", 800.0, "Electronics", true),
            Product("Headphones", 150.0, "Electronics", true),
            Product("Shoes", 120.0, "Fashion", false),
            Product("T-Shirt", 40.0, "Fashion", true),
            Product("Coffee Maker", 200.0, "Home", true)
        )

        val output = StringBuilder()

        val available = products.filter { it.inStock }

        val discount: DiscountRule = { it * 0.9 }

        val discounted = products.map {
            applyDiscount(it, discount)
        }

        val grouped = products.groupBy { it.category }

        val total = products.fold(0.0) { acc, product ->
            acc + product.price
        }

        val sorted = products.sortedBy { it.price }

        output.append("AVAILABLE PRODUCTS:\n")
        available.forEach {
            output.append("${it.name}\n")
        }

        output.append("\nDISCOUNTED PRODUCTS:\n")
        discounted.forEach {
            output.append("${it.name} - $${"%.2f".format(it.price)}\n")
        }

        output.append("\nGROUPED BY CATEGORY:\n")
        grouped.forEach { (category, items) ->
            output.append("$category:\n")
            items.forEach {
                output.append("  - ${it.name}\n")
            }
        }

        output.append("\nTotal Price: $${"%.2f".format(total)}\n")

        output.append("\nSORTED BY PRICE:\n")
        sorted.forEach {
            output.append("${it.name} - $${it.price}\n")
        }

        textView.text = output.toString()
    }
}