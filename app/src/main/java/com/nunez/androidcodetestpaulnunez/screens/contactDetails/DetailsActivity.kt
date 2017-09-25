package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.LocalRepository
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import com.nunez.androidcodetestpaulnunez.screens.addEditContact.AddEditActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.content_details.*
import kotlinx.android.synthetic.main.details_activity.*


class DetailsActivity : AppCompatActivity(), DetailsContract.View {

    companion object {
        const val EXTRA_CONTACT_ID = "contact_id"
    }

    var contactId = ""
    lateinit var presenter: DetailsContract.Presenter
    lateinit var interactor: DetailsContract.Interactor
    lateinit var repository: RepositoryContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        setSupportActionBar(toolbar)

        instantateDepenencies()

        supportActionBar?.apply {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        toolbar.apply {
            setTitleTextColor(this.resources.getColor(R.color.white))
            navigationIcon = resources.getDrawable(R.drawable.ic_arrow_back_white)
        }

        favoriteButton.setOnClickListener { favoriteButtonListener() }

        contactId = intent.getStringExtra(EXTRA_CONTACT_ID)
        presenter.requestContact(contactId)

        contactImage.setAspectRatio()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.action_edit){
            editButtonListener()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun instantateDepenencies() {
        repository = LocalRepository(Realm.getDefaultInstance())
        interactor = DetailsInteractor(repository)
        presenter = DetailsPresenter(this, interactor)
    }

    private fun convertDpToPx(dp: Int): Int {
        return Math.round(dp * (resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

    }

    private fun addViewToList(list: LinearLayout, value: String, isAddress: Boolean = false) {

        val style = if (isAddress)
            R.style.DetailsScreenText_Address
        else
            R.style.DetailsScreenText

        val textView = TextView(this, null, 0, style)

        textView.apply {
            height = convertDpToPx(50)
            text = value
            setOnClickListener { view ->
                //                phoneClickListener((view as TextView).text as String)
            }
        }

        list.addView(textView)
    }

    override fun showContact(contact: Contact) {

        with(contact) {
            supportActionBar?.title =  firstName + " " + lastName

            if (phonenumbers.isNotEmpty()) {
                for (phone in phonenumbers)
                    addViewToList(phoneList, phone.number)
            }

            if (emails.isNotEmpty()) {
                for (email in emails) {
                    addViewToList(emailList, email.email)
                }
            } else {
                emailList.visibility = View.GONE
            }

            if (adresses.isNotEmpty()) {
                for (address in adresses) {
                    addViewToList(addressList, address.address)
                }
            } else {
                addressList.visibility = View.GONE
            }
        }

        birthday.text = contact.birthday
        Log.i(this@DetailsActivity.localClassName, contact.toString())
    }

    override fun phoneClickListener(number: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun birthdayClickListener(birthday: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addressClickListener(address: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun favoriteButtonListener() {
       presenter.onFavoriteClick(contactId)
    }

    override fun editButtonListener() {
        presenter.onEditButtonClicked()
    }

    override fun setFavorite() {
        favoriteButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.yellow))
    }

    override fun unsetFavorite() {
        favoriteButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this,R.color.light_gray))
    }

    override fun gotoEditActivity() {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra(AddEditActivity.EXTRA_CONTACT_ID, contactId)
        startActivity(intent)
    }
}
