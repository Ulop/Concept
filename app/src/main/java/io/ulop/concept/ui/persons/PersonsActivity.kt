package io.ulop.concept.ui.persons

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.ulop.concept.R
import io.ulop.concept.adapter.makeAdapter
import io.ulop.concept.adapter.personDelegate
import io.ulop.concept.data.ListItem
import io.ulop.concept.data.PersonRepository
import kotlinx.android.synthetic.main.activity_root.*
import org.koin.android.ext.android.inject

class PersonsActivity : AppCompatActivity() {

    private val adapter by makeAdapter {
        personDelegate()
    }

    private val viewModel: PersonsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_root)

        setSupportActionBar(toolbar)
        supportActionBar?.title = "Persons"

        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recycler.adapter = adapter
        viewModel.getPersons().observe(this, Observer { persons ->
            adapter.setData(persons?.map {
                ListItem.Person(
                        id = it.id,
                        name = "${it.name} ${it.surname}",
                        about = it.about,
                        avatar = it.avatar
                )
            } ?: emptyList())
        })

    }
}
