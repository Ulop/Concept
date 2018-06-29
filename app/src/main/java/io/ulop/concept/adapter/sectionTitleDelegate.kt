package io.ulop.concept.adapter

import android.support.v4.content.ContextCompat
import android.view.View
import io.ulop.concept.R
import io.ulop.concept.base.ext.append
import io.ulop.concept.base.ext.buildSpanned
import io.ulop.concept.base.ext.foregroundColor
import io.ulop.concept.data.ListItem
import kotlinx.android.synthetic.main.partial_section_title.view.*

fun AbstractAdapter.sectionTitleDelegate() = delegate<ListItem.SectionTitle>(R.layout.partial_section_title) { vh, title ->
    vh.itemView.title.text = buildSpanned {
        append(title.title.toUpperCase())
        title.subTitle?.let { subTitle ->
            append(
                    "  $subTitle",
                    foregroundColor(ContextCompat.getColor(vh.itemView.context, R.color.textColorSecondary))
            )
        }
    }

    with(vh.itemView.button){
        if (title.buttonText != null) {
            visibility = View.VISIBLE
            text = title.buttonText
            setOnClickListener { title.onButtonClick?.invoke(title) }
        } else visibility = View.INVISIBLE
    }
}