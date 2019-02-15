package com.zhai.caloriecalculator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(private val items: MutableList<Pair<String, Int>>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View =
            (convertView ?: LayoutInflater.from(parent?.context).inflate(R.layout.item, parent, false))
                .apply {
                    findViewById<TextView>(R.id.food_name).text = items[position].first
                    findViewById<TextView>(R.id.food_calorie).text = items[position].second.toString()
                }

    override fun getItem(position: Int) = items[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = items.size

    //add item by finding it first.
    fun addItem(item : Pair<String, Int>) {
        items.indexOfFirst{ it.first == item.first }.let {
            when(it) {
                -1 -> items.add(item)
                else ->  {
                    val itemToRemove = items[it]
                    items.removeAt(it)
                    items.add(it, item.first to item.second+itemToRemove.second)
                    return@let
                }
            }
        }
        notifyDataSetChanged()
    }

    public fun getTotalCalories() = items.sumBy { it.second }
}