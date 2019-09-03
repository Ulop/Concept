package io.ulop.concept.ui.person

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import io.ulop.concept.R
import io.ulop.concept.adapter.friendsDialogDelegate
import io.ulop.concept.adapter.makeAdapter
import io.ulop.concept.base.viewstate.ViewState
import io.ulop.concept.data.ListItem
import kotlinx.android.synthetic.main.dialog_fragment_friends.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FriendSelectDialog : androidx.fragment.app.DialogFragment() {
    private val personPageViewModel: PersonPageViewModel by sharedViewModel()
    private val adapter by makeAdapter {
        friendsDialogDelegate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recycler.adapter = adapter

        requireActivity()

        personPageViewModel.personInfo.observe(this, Observer {
            it?.first?.let { person ->
                adapter.setData(person.friends.map {
                    ListItem.Person(
                            id = it.id,
                            name = it.name,
                            about = it.about,
                            avatar = it.avatar,
                            action = { id ->
                                personPageViewModel.personId.setValue(id)
                                dismiss()
                            }
                    )
                })
            }
        })
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        personPageViewModel.setState(ViewState.Idle)
    }

}