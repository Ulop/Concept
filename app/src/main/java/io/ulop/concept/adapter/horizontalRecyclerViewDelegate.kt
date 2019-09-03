package io.ulop.concept.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import io.ulop.concept.R
import io.ulop.concept.data.ListItem

inline fun <reified H : ListItem, reified IT : ListItem>
        AbstractAdapter.horizontalRecyclerDelegate(
        layout: Int,
        crossinline mapper: (H) -> List<IT>,
        crossinline block: (BaseVH, IT) -> Unit) = delegate<H>(R.layout.item_horizonatl_recycler) { view, data ->
    (view.itemView as androidx.recyclerview.widget.RecyclerView).let { recyclerView ->


        recyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(recyclerView.context, androidx.recyclerview.widget.RecyclerView.HORIZONTAL, false)

        val list = mapper(data)
        val adapter by makeAdapter { delegate(layout, block) }
        adapter.setData(list)

        recyclerView.adapter = adapter
    }
}