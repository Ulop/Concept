package io.ulop.concept.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AbsListItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.ListDelegationAdapter
import io.ulop.concept.base.ext.inflate
import io.ulop.concept.data.ListItem

inline fun <reified T : ListItem> AbstractAdapter.delegate(layout: Int, crossinline block: (BaseVH, T) -> Unit) {
    val d: AbsListItemAdapterDelegate<T, ListItem, BaseVH> = object : AbsListItemAdapterDelegate<T, ListItem, BaseVH>() {
        override fun onCreateViewHolder(parent: ViewGroup): BaseVH {
            return BaseVH(parent.inflate(layout))
        }

        override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean {
            return item is T
        }

        override fun onBindViewHolder(item: T, viewHolder: BaseVH, payloads: MutableList<Any>) {
            block(viewHolder, item)
        }

    }
    this.addDelegate(d)
}

inline fun <reified T : ListItem> newDelegate(layout: Int, crossinline block: (View, T) -> Unit) =
        object : AbsListItemAdapterDelegate<T, ListItem, androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder {
                return BaseVH(parent.inflate(layout))
            }

            override fun isForViewType(item: ListItem, items: MutableList<ListItem>, position: Int): Boolean {
                return item is T
            }

            override fun onBindViewHolder(item: T, viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, payloads: MutableList<Any>) {
                block(viewHolder.itemView, item)
            }

        }

class BaseVH(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
    companion object {
        var count = 0
    }

    init {
        println("BaseVH instance count ${++count}")
    }
}

class AbstractAdapter(delegates: List<AdapterDelegate<MutableList<ListItem>>> = emptyList()) : ListDelegationAdapter<MutableList<ListItem>>() {
    init {
        items = mutableListOf()
        delegates.forEach { delegate ->
            delegatesManager.addDelegate(delegate)
        }
    }


    fun addDelegate(delegate: AdapterDelegate<MutableList<ListItem>>) {
        delegatesManager.addDelegate(delegate)
    }

    fun setData(data: List<ListItem>): AbstractAdapter {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
        return this
    }
}

fun makeAdapter(builder: AbstractAdapter.() -> Unit) = lazy {
    AbstractAdapter().apply(builder)
}