package com.zhai.caloriecalculator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_food.*
import java.lang.Exception

class AddFoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        submit.setOnClickListener {
            try {
                val name = food_name.text.toString().replace("\\d".toRegex(), "").trim()
                val value = food_calorie.text.toString()
                if(name.isEmpty() || value.isEmpty()) {
                    Toast.makeText(it.context, "Please enter valid input", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if(name.toIntOrNull() != null) {
                    Toast.makeText(it.context, "Please enter valid name", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                //send data back to main activity.
                setResult(Activity.RESULT_OK, Intent()
                        .putExtra("name", name)
                        .putExtra("value", value.toInt()))

                finish()
            }
            catch (e : Exception) {
                Toast.makeText(it.context, "Please enter valid input", Toast.LENGTH_SHORT).show()
            }
        }
    }
}