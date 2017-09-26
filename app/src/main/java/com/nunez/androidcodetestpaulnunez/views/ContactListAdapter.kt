package com.nunez.androidcodetestpaulnunez.views

import android.support.v7.widget.RecyclerView
import android.util.Log
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
        val clickListener: (id: String, imageView: View) -> (Unit),
        val longClickListener: (id: String, transitionName: String) -> (Unit)
) : RecyclerView.Adapter<ContactListAdapter.BaseHolder>() {

    override fun onBindViewHolder(holder: BaseHolder?, position: Int) {
        val contact = list[position]
        if (isFavoriteList)
            (holder as FavoriteHolder).bind(contact, "favorite_contact_$position")
        else
            (holder as ContactHolder).bind(contact, "normal_contact_$position")
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
                              val clickListener: (id: String, imageView: View) -> (Unit),
                              val longClickListener: (id: String, transitionName: String) -> (Unit)
    ) : RecyclerView.ViewHolder(itemView) {

        open fun bind(contact: Contact, transitionName: String) {}
    }

    class FavoriteHolder(itemView: View,
                         clickListener: (id: String, imageView: View) -> (Unit),
                         longClickListener: (id: String, transitionName: String) -> (Unit)
    ) : BaseHolder(itemView, clickListener, longClickListener) {

        override fun bind(contact: Contact, transitionName: String) {
            Log.d("FavoriteHolder", "transitionName: $transitionName")

            with(itemView) {
                favoriteName.text = contact.firstName
                favoriteImage.transitionName = transitionName
                isLongClickable = true

                setOnClickListener {
                    Log.d("FavoriteHolder", "transitionName: ${favoriteImage.transitionName}")
                    clickListener(contact.id, itemView.favoriteImage)
                }

                setOnLongClickListener {
                    longClickListener(contact.id, transitionName)
                    true
                }
            }

        }
    }

    class ContactHolder(itemView: View,
                        clickListener: (id: String, imageView: View) -> (Unit),
                        longClickListener: (id: String, transitionName: String) -> (Unit)
    ) : BaseHolder(itemView, clickListener, longClickListener) {

        override fun bind(contact: Contact, transitionName: String) {

            val contactName = contact.firstName + " " + contact.lastName

            with(itemView) {
                name.text = contactName
                phone.text = contact.phonenumbers[0].number
                image.transitionName = transitionName
                isLongClickable = true

                setOnClickListener {
                    Log.d("ContactHolder", "transitionName: ${image.transitionName}")
                    clickListener(contact.id, itemView.image)
                }
                setOnLongClickListener {
                    longClickListener(contact.id, transitionName)
                    true
                }
            }
        }
    }
}

