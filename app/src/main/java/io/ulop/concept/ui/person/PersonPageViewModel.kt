package io.ulop.concept.ui.person

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.ulop.concept.base.livedata.StackLiveData
import io.ulop.concept.base.viewstate.ViewState
import io.ulop.concept.data.ListItem
import io.ulop.concept.data.Person
import io.ulop.concept.data.PersonRepository
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

class PersonPageViewModel(
        id: String,
        private val personRepository: PersonRepository) : ViewModel() {

    val personInfo = MutableLiveData<Pair<Person, List<ListItem>>>()
    val clickAction = MutableLiveData<Int>()

    val personId = StackLiveData<String>()

    val viewState = MutableLiveData<ViewState>()

    companion object {
        const val SECTION_ABOUT   = 0x0001
        const val SECTION_FRIENDS = 0x0002
        const val SECTION_SHOTS   = 0x0003
    }

    init {
        viewState.value = ViewState.Idle
        personId.setValue(id)
        personId.observeForever { pID ->
            if (pID != null) {
                launch(UI) {
                    async {
                        personRepository.getPersons().find { it.id == personId.value }
                    }.await().let { person ->
                        if (person != null) {
                            personInfo.value = person to listOf(
                                    ListItem.SectionTitle(
                                            "About", "Show all text",
                                            "See all",
                                            {
                                                viewState.value = ViewState.ItemClick(it)
                                                viewState.value = ViewState.Idle

                                            },
                                            SECTION_ABOUT
                                    ),
                                    ListItem.ExpandableText("${person.about}\n${person.about}"),
                                    ListItem.SectionTitle(
                                            "Friends", null,
                                            "See all",
                                            {
                                                viewState.value = ViewState.ItemClick(it)
                                                viewState.value = ViewState.Idle
                                            },
                                            SECTION_FRIENDS

                                    ),
                                    ListItem.PersonList(person.friends.map {
                                        ListItem.Person(
                                                id = it.id,
                                                name = it.name,
                                                about = it.about,
                                                avatar = it.avatar,
                                                action = { id ->
                                                    personId.setValue(id)
                                                }
                                        )
                                    }),
                                    ListItem.SectionTitle("Shots", id = SECTION_SHOTS)
                            ) + person.shots.map {
                                ListItem.Shot(it.url, it.color)
                            }
                        }
                    }
                }
            }
        }

    }

}