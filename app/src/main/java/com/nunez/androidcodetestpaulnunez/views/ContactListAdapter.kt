package com.nunez.androidcodetestpaulnunez.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import kotlinx.android.synthetic.main.item_contact.view.*
import kotlinx.android.synthetic.main.item_favorite_contact.view.*

class ContactListAdapter(
        var list: List<Contact>,
        val isFavoriteList: Boolean = false,
        val clickListener: (id: String) -> (Unit),
        val longClickListener: (id: String) -> (Unit)
) : RecyclerView.Adapter<ContactListAdapter.BaseHolder>() {

    override fun onBindViewHolder(holder: BaseHolder?, position: Int) {
        val contact = list[position]
        if (isFavoriteList)
            (holder as FavoriteHolder).bind(contact)
        else
            (holder as ContactHolder).bind(contact)
    }

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseHolder {
        if (isFavoriteList) {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_favorite_contact, parent, false)
            return FavoriteHolder(view, clickListener, longClickListener)
        } else {
            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_contact, parent, false)
            return ContactHolder(view, clickListener, longClickListener)
        }
    }

    abstract class BaseHolder(itemView: View,
                              val clickListener: (id: String) -> Unit,
                              val longClickListener: (id: String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        open fun bind(contact: Contact ) {
            itemView.setOnClickListener { clickListener(contact.id) }
            itemView.isLongClickable = true
            itemView.setOnLongClickListener {
                longClickListener(contact.id)
                true
            }
        }
    }

    class FavoriteHolder(itemView: View,
                         clickListener: (id: String) -> Unit,
                         longClickListener: (id: String) -> Unit
    ) : BaseHolder(itemView, clickListener, longClickListener) {
        override fun bind(contact: Contact) {
            super.bind(contact)

            itemView.favoriteName.text = contact.firstName
        }
    }

    class ContactHolder(itemView: View,
                        clickListener: (id: String) -> Unit,
                        longClickListener: (id: String) -> Unit
    ) : BaseHolder(itemView, clickListener, longClickListener) {

        override fun bind(contact: Contact) {
            super.bind(contact)

            val contactName = contact.firstName + " " + contact.lastName
            with(itemView) {
                name.text = contactName
                phone.text = contact.phonenumbers[0].number
            }
        }
    }
}

