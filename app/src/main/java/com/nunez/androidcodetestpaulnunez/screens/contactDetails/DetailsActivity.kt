package com.nunez.androidcodetestpaulnunez.screens.contactDetails

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
    var contactName = ""
    var contactBirthday = ""
    var rowHeight = 0
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
        rowHeight = convertDpToPx(50)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_edit) {
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

    private fun addPhoneToList(number: String) {
        val textView = TextView(this, null, 0, R.style.DetailsScreenText)
        textView.apply {
            height = rowHeight
            text = number
            setOnClickListener { view ->
                phoneClickListener(number)
            }
        }
        phoneList.addView(textView)
    }

    private fun addAddressToList(address: String) {
        val textView = TextView(this, null, 0, R.style.DetailsScreenText_Address)
        textView.apply {
            //            height = rowHeight
            text = address
            setOnClickListener { view ->
                addressClickListener(address)
            }
        }
        addressList.addView(textView)
    }

    private fun addEmailToList(email: String) {
        val textView = TextView(this, null, 0, R.style.DetailsScreenText)
        textView.apply {
            height = rowHeight
            text = email
            setOnClickListener { view ->
                emailClickListener(email)
            }
        }
        emailList.addView(textView)
    }

    override fun showContact(contact: Contact) {

        with(contact) {
            supportActionBar?.title = firstName + " " + lastName

            if (phonenumbers.isNotEmpty()) {
                for (phone in phonenumbers)
                    addPhoneToList(phone.number)
            }

            if (emails.isNotEmpty()) {
                for (email in emails) {
                    addEmailToList(email.email)
                }
            } else {
                emailList.visibility = View.GONE
                emailImage.visibility = View.GONE
            }

            if (adresses.isNotEmpty()) {
                for (address in adresses) {
                    addAddressToList(address.address)
                }
            } else {
                addressList.visibility = View.GONE
                addressImage.visibility = View.GONE
            }
        }

        if (contact.birthday.isNotEmpty()) {
            birthday.text = contact.birthday
            birthday.setOnClickListener { birthdayClickListener(contact.birthday) }
        } else {
            birthdayImage.visibility = View.GONE
            birthday.visibility = View.GONE
        }

    }

    override fun phoneClickListener(number: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.apply {
            data = Uri.parse("tel:" + number)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
    }

    override fun birthdayClickListener(birthday: String) {
    }

    override fun addressClickListener(address: String) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = gmmIntentUri
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun emailClickListener(email: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = Uri.parse("mailto:").toString()
        intent.putExtra(Intent.EXTRA_EMAIL, email)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    override fun favoriteButtonListener() {
        presenter.onFavoriteClick(contactId)
    }

    override fun editButtonListener() {
        presenter.onEditButtonClicked()
    }

    override fun setFavorite() {
        favoriteButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.yellow))
    }

    override fun unsetFavorite() {
        favoriteButton.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.light_gray))
    }

    override fun gotoEditActivity() {
        val intent = Intent(this, AddEditActivity::class.java)
        intent.putExtra(AddEditActivity.EXTRA_CONTACT_ID, contactId)
        startActivity(intent)
    }
}
