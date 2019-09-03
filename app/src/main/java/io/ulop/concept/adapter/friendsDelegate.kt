package io.ulop.concept.adapter

import androidx.core.view.MarginLayoutParamsCompat
import android.view.ViewGroup
import io.ulop.concept.R
import io.ulop.concept.base.Action
import io.ulop.concept.base.ext.px
import io.ulop.concept.common.GlideApp
import io.ulop.concept.data.ListItem
import io.ulop.concept.ui.person.PersonPageViewModel
import kotlinx.android.synthetic.main.item_friends_short.view.*

fun AbstractAdapter.friendsDelegate() = horizontalRecyclerDelegate<ListItem.PersonList, ListItem.Person>(
        layout = R.layout.item_friends_short,
        mapper = { it.persons }
) { vh, person ->
    GlideApp.with(vh.itemView)
            .load(person.avatar)
            .circleCrop()
            .into(vh.itemView.friendAvatar)

    vh.itemView.name.text = person.name
    if (vh.adapterPosition == 0) {
        val lp = vh.itemView.layoutParams as ViewGroup.MarginLayoutParams
        MarginLayoutParamsCompat.setMarginStart(lp, 16.px)
        vh.itemView.layoutParams = lp
    }

    vh.itemView.setOnClickListener { person.action(person.id) }
}