package io.ulop.concept.ui.person

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ulop.concept.base.livedata.StackLiveData
import io.ulop.concept.base.viewstate.ViewState
import io.ulop.concept.data.ListItem
import io.ulop.concept.data.Person
import io.ulop.concept.data.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonPageViewModel(
        id: String,
        private val personRepository: PersonRepository) : ViewModel() {

    val personInfo = MutableLiveData<Pair<Person, List<ListItem>>>()

    val personId = StackLiveData<String>()

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState>
        get() = _viewState


    companion object {
        const val SECTION_ABOUT = 0x0001
        const val SECTION_FRIENDS = 0x0002
        const val SECTION_SHOTS = 0x0003
    }

    init {
        _viewState.value = ViewState.Idle
        personId.setValue(id)
        personId.observeForever { pID ->
            if (pID != null) {
                viewModelScope.launch {
                    withContext(Dispatchers.Default) {
                        personRepository.getPersons().find { it.id == personId.value }
                    }.let { person ->
                        if (person != null) {
                            personInfo.value = person to listOf(
                                    ListItem.SectionTitle(
                                            "About", "Show all text",
                                            "See all",
                                            {
                                                _viewState.value = ViewState.ItemClick(it)
                                                _viewState.value = ViewState.Idle

                                            },
                                            SECTION_ABOUT
                                    ),
                                    ListItem.ExpandableText("${person.about}\n${person.about}"),
                                    ListItem.SectionTitle(
                                            "Friends", null,
                                            "See all",
                                            {
                                                _viewState.value = ViewState.ItemClick(it)
                                                _viewState.value = ViewState.Idle
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

    fun setState(sate : ViewState){
        _viewState.value = sate
    }

}