package com.zhai.caloriecalculator

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.ArrayList

const val REQUEST_CODE_ADD_FOOD = 28374

class MainActivity : AppCompatActivity() {

    private val adapter  by lazy {
        ListAdapter(ArrayList())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //dialog
        val alertView = LayoutInflater.from(this@MainActivity).inflate(R.layout.dialog, null, false);
        AlertDialog.Builder(this)
            .apply {
                setView(alertView)
                setCancelable(false)
                setFinishOnTouchOutside(false)
            }
            .create()
            .apply {
                //setting views
                val editText = alertView.findViewById<EditText>(R.id.total_calories_input)
                val submit = alertView.findViewById<Button>(R.id.submit)

                submit?.setOnClickListener {view ->
                    val totalCalorieString = editText?.text.toString()
                    try {
                        val totalCalorie = Integer.parseInt(totalCalorieString)
                        if(totalCalorie > 0){
                            //set values for total and remaining
                            this@MainActivity.total_calories_value.text = "$totalCalorie"
                            this@MainActivity.calorie_remaining_value.text = "$totalCalorie"

                            dismiss()
                        }
                        else {
                            //show error
                            Toast.makeText(view.context, "Please enter valid input", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e : Exception) {
                        Toast.makeText(view.context, "Please enter valid input", Toast.LENGTH_SHORT).show()
                    }
                }
                show()
            }

        //button click
        findViewById<Button>(R.id.button)?.setOnClickListener {
            startActivityForResult(Intent(this, AddFoodActivity::class.java), REQUEST_CODE_ADD_FOOD)
        }

        //listadapter
        val listView = findViewById<ListView>(R.id.list_view)
        listView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE_ADD_FOOD && resultCode == Activity.RESULT_OK){
            data?.extras?.let {extras ->
                //get the data from activity
                val name = extras.getString("name")
                val calorie = extras.getInt("value")
                adapter.addItem(name to calorie)

                val remainingCalories = total_calories_value.text.toString().toInt() - adapter.getTotalCalories()
                calorie_remaining_value.text = remainingCalories.toString()
                //change color if needed.
                if(remainingCalories < 0){
                    calorie_remaining_value.setTextColor(Color.RED)
                }
            }
        }
    }
}
