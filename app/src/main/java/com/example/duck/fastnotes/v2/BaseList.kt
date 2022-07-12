package com.example.duck.fastnotes.v2

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

object BaseList {

    abstract class Adapter<T : Any, S : Any> : RecyclerView.Adapter<ViewHolder<T, S>>() {

        var data: List<T> = emptyList()
            protected set

        fun setDataAndNotifyAll(list: List<T>) {
            notifyItemRangeRemoved(0, data.size)
            data = list
            notifyItemRangeInserted(0, data.size)
        }

        private val selectedItemEmitter = MutableSharedFlow<S>(extraBufferCapacity = 1)
        val selectedItem: Flow<S> = selectedItemEmitter

        override fun onBindViewHolder(holder: ViewHolder<T, S>, position: Int) = with(holder) {
            bind(item = data[position]) { itemSelectedResult ->
                selectedItemEmitter.tryEmit(itemSelectedResult)
            }
        }

        override fun onViewRecycled(holder: ViewHolder<T, S>) = holder.unbind()
        override fun getItemCount(): Int = data.size
    }

    abstract class ViewHolder<in T : Any, out S : Any>(
        parent: ViewGroup,
        @LayoutRes itemLayoutId: Int
    ) : RecyclerView.ViewHolder(parent.inflate(itemLayoutId)) {
        abstract fun bind(item: T, onItemSelected: (S) -> Unit)
        abstract fun unbind()
    }
}
