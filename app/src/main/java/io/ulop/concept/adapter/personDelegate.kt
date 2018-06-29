package io.ulop.concept.adapter

import android.view.View
import android.widget.Toast
import io.ulop.concept.R
import io.ulop.concept.base.ext.startActivity
import io.ulop.concept.common.GlideApp
import io.ulop.concept.data.ListItem
import io.ulop.concept.ui.person.PersonPageActivity
import kotlinx.android.synthetic.main.item_person_list.view.*

fun AbstractAdapter.personDelegate() = delegate(R.layout.item_person_list) { vh, person: ListItem.Person ->
    vh.itemView.name.text = person.name
    vh.itemView.about.text = person.about

    GlideApp.with(vh.itemView)
            .load(person.avatar)
            .circleCrop()
            .into(vh.itemView.avatar)

    vh.itemView.setOnClickListener {
        it.context.startActivity<PersonPageActivity>(
                "PERSON_ID" to person.id
        )
    }

}