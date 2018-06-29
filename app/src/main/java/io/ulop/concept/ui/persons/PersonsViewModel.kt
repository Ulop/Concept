package io.ulop.concept.ui.persons

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.ulop.concept.data.Person
import io.ulop.concept.data.PersonRepository
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.withContext

class PersonsViewModel(private val personRepository: PersonRepository) : ViewModel() {
    fun getPersons(): LiveData<List<Person>> {
        val data = MutableLiveData<List<Person>>()
        data.value = runBlocking {
            async { personRepository.getPersons() }.await()
        }
        return data
    }
}
