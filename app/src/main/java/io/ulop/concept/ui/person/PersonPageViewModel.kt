package io.ulop.concept.ui.person

import androidx.lifecycle.*
import io.ulop.concept.base.event.Event
import io.ulop.concept.base.event.EventObserver
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

    val viewState = MutableLiveData<Event<ViewState>>()


    companion object {
        const val SECTION_ABOUT = 0x0001
        const val SECTION_FRIENDS = 0x0002
        const val SECTION_SHOTS = 0x0003
    }

    private val personIdObserver = Observer<String> { pID ->
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                personRepository.getPerson(pID)
            }.let { person ->
                personInfo.value = person to listOf(
                        ListItem.SectionTitle(
                                "About", "Show all text",
                                "See all",
                                {
                                    viewState.postValue(Event(ViewState.ItemClick(it)))

                                },
                                SECTION_ABOUT
                        ),
                        ListItem.ExpandableText("${person.about}\n${person.about}"),
                        ListItem.SectionTitle(
                                "Friends", null,
                                "See all",
                                {
                                    viewState.postValue(Event(ViewState.ItemClick(it)))
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


    init {
        viewState.value = Event(ViewState.Idle)
        personId.setValue(id)
        personId.observeForever(personIdObserver)

    }

    override fun onCleared() {
        personId.removeObserver(personIdObserver)
        super.onCleared()
    }

    fun setState(sate: ViewState) {
        viewState.value = Event(sate)
    }

}