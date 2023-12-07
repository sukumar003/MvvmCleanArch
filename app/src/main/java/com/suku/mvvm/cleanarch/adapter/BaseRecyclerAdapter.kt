package com.suku.mvvm.cleanarch.adapter

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<T, V : BaseViewHolder<T>> : RecyclerView.Adapter<V>() {

    //T mean response
    //V mean viewHolder

    protected var TAG = javaClass.simpleName

    var data: MutableList<T> = mutableListOf()

    fun getAllItems(): List<T> {
        return data
    }

    override fun onBindViewHolder(holder: V, position: Int) {
        holder.bindData(getItem(position), position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @Throws(IndexOutOfBoundsException::class)
    fun getItem(position: Int): T {
        return data[position]
    }

    fun addItem(`object`: T) {
        data.add(`object`)
        notifyItemInserted(data.indexOf(`object`))
    }

    fun restoreItem(item: T, position: Int) {
        data.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    @Throws(IndexOutOfBoundsException::class)
    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItems(data: MutableList<T>?) {
        if (data != null) {
            val startRange = if (data.size - 1 > 0)
                data.size - 1
            else 0
            this.data.addAll(data)
            notifyItemRangeChanged(startRange, data.size)
        }
    }
}