package io.ulop.concept.data

import io.ulop.concept.base.Action

sealed class ListItem {
    data class Person(
            val id: String,
            val name: String,
            val about: String,
            val avatar: String,
            val action: (String) -> Unit = {}
    ) : ListItem()

    data class SectionTitle(
            val title: String,
            val subTitle: String? = null,
            val buttonText: String? = null,
            val onButtonClick: ((SectionTitle) -> Unit)? = null,
            val id: Int? = null
    ) : ListItem()

    data class ExpandableText(
            val text: String,
            val maxLines: Int = 3,
            var expanded: Boolean = false
    ) : ListItem()

    class PersonList(val persons: List<ListItem.Person>) : ListItem()
    class Shot(val url: String, val color: Int, val action: (String) -> Unit = {}) : ListItem()
}