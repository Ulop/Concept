package io.ulop.concept.di

import io.ulop.concept.data.PersonRepository
import io.ulop.concept.data.RandomPersonRepository
import io.ulop.concept.data.RoomPersonRepository
import io.ulop.concept.ui.person.PersonPageViewModel
import io.ulop.concept.ui.persons.PersonsViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val conceptModule = applicationContext {
    bean("random") { RandomPersonRepository() as PersonRepository }
    bean("room") { RoomPersonRepository(androidApplication()) as PersonRepository }
    viewModel { params -> PersonPageViewModel(params["id"], get("room")) }
    viewModel { PersonsViewModel(get("room")) }
}