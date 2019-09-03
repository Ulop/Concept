package io.ulop.concept.ui.person

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import io.ulop.concept.R
import io.ulop.concept.adapter.*
import io.ulop.concept.base.PersonInfoObserver
import io.ulop.concept.base.ext.appendCounter
import io.ulop.concept.base.ext.argument
import io.ulop.concept.base.flow.asFlow
import io.ulop.concept.base.viewstate.ViewState
import io.ulop.concept.common.GlideApp
import io.ulop.concept.data.ListItem
import io.ulop.concept.data.Person
import kotlinx.android.synthetic.main.activity_person_page.*
import kotlinx.android.synthetic.main.partial_header_buttons.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class PersonPageActivity : AppCompatActivity(), PersonInfoObserver {

    private val id by argument<String>("PERSON_ID")
    private val personViewModel: PersonPageViewModel by viewModel { parametersOf(id) }

    private val adapter by makeAdapter {
        sectionTitleDelegate()
        expandableTextDelegate()
        friendsDelegate()
        imageDelegate()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_page)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)
        supportActionBar?.title = ""
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler.adapter = adapter


        personViewModel.personInfo.observe(this, this)

        lifecycleScope.launch {
            personViewModel.viewState.asFlow()
                    .mapNotNull { if (it is ViewState.ItemClick<*> && it.item is ListItem.SectionTitle) it.item else null }
                    .collect {
                        when (it.id) {
                            PersonPageViewModel.SECTION_ABOUT -> {
                                val index = adapter.items.indexOfFirst { it is ListItem.ExpandableText }
                                val text = adapter.items[index] as ListItem.ExpandableText
                                text.expanded = !text.expanded
                                adapter.notifyItemChanged(index, arrayListOf(""))
                            }
                            PersonPageViewModel.SECTION_FRIENDS -> {
                                FriendSelectDialog().show(supportFragmentManager, "FSD")
                            }
                        }
                    }
            personViewModel.viewState.asFlow()
                    .mapNotNull { (it === ViewState.Idle) }
                    .filter { it }
                    .collect { }
        }



        shots.setOnClickListener {
            val index = adapter.items
                    .indexOfFirst { it is ListItem.SectionTitle && it.id == PersonPageViewModel.SECTION_SHOTS }
            if (index != -1)
                recycler.smoothScrollToPosition(index)
        }

        friends.setOnClickListener {
            val index = adapter.items
                    .indexOfFirst { it is ListItem.SectionTitle && it.id == PersonPageViewModel.SECTION_FRIENDS }
            if (index != -1)
                recycler.smoothScrollToPosition(index)
        }
    }

    override fun onBackPressed() {
        if (personViewModel.personId.pop() == null)
            super.onBackPressed()
    }

    override fun onChanged(info: Pair<Person, List<ListItem>>?) {
        info?.let { (person, list) ->
            GlideApp.with(avatar)
                    .load(person.avatar)
                    .circleCrop()
                    .into(avatar)

            person.shots.firstOrNull()?.let { first ->
                GlideApp.with(avatar)
                        .load(first.url + "&blur")
                        .centerCrop()
                        .placeholder(ColorDrawable(first.color))
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(app_bar_image)
            }

            supportActionBar?.title = ""
            name.text = "${person.name} ${person.surname}"
            subTitle.text = person.place


            placesLabel.appendCounter(person.places.size)
            alertsLabel.appendCounter(person.alerts.size)
            shotsLabel.appendCounter(person.shots.size)
            freindsLabel.appendCounter(person.friends.size)

            adapter.setData(list)
            Unit
        }
    }

}
