package io.ulop.concept.adapter

import io.ulop.concept.R
import io.ulop.concept.common.GlideApp
import io.ulop.concept.data.ListItem
import kotlinx.android.synthetic.main.item_person_friend_list.view.*

fun AbstractAdapter.friendsDialogDelegate() = delegate<ListItem.Person>(R.layout.item_person_friend_list){ baseVH, person ->
    baseVH.itemView.name.text = person.name

    GlideApp.with(baseVH.itemView)
            .load(person.avatar)
            .circleCrop()
            .into(baseVH.itemView.avatar)

    baseVH.itemView.setOnClickListener { person.action(person.id) }
}