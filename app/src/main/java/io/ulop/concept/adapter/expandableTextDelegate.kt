package io.ulop.concept.adapter

import android.widget.TextView
import io.ulop.concept.R
import io.ulop.concept.data.ListItem

fun AbstractAdapter.expandableTextDelegate() = delegate<ListItem.ExpandableText>(R.layout.item_expandable_text) { view, textItem ->
    (view.itemView as TextView).let { textView ->
        textView.text = textItem.text
        textView.maxLines = if (textItem.expanded) Integer.MAX_VALUE else textItem.maxLines
    }
}