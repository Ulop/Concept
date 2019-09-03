package io.ulop.concept.ui.persons

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ulop.concept.data.Person
import io.ulop.concept.data.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonsViewModel(private val personRepository: PersonRepository) : ViewModel() {
    fun getPersons(): LiveData<List<Person>> {
        val data = MutableLiveData<List<Person>>()
        viewModelScope.launch {
            data.value = withContext(Dispatchers.Default) { personRepository.getPersons() }
        }
        return data
    }
}
