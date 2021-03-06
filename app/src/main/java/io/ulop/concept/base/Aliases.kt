package io.ulop.concept.base

import androidx.lifecycle.Observer
import io.ulop.concept.data.ListItem
import io.ulop.concept.data.Person

typealias Action = () -> Unit
typealias ClickObserver = (Int?) -> Unit
typealias PersonInfoObserver = Observer<Pair<Person, List<ListItem>>>