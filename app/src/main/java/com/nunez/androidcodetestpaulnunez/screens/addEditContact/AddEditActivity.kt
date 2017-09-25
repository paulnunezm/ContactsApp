package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.LocalRepository
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import com.nunez.androidcodetestpaulnunez.screens.contactList.ListActivity
import io.realm.Realm
import kotlinx.android.synthetic.main.add_edit_activity.*
import kotlinx.android.synthetic.main.content_add_edit.*

class AddEditActivity : AppCompatActivity(), AddEditContract.View {

    companion object {
        val LOG_TAG = AddEditActivity::class.java.simpleName.toString()
        const val EXTRA_CONTACT_ID = "contact_id"
    }

    var edit_mode = false
    var contactId: String? = null

    lateinit var presenter: AddEditContract.Presenter
    lateinit var interactor: AddEditContract.Interactor
    lateinit var repository: RepositoryContract

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_activity)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(resources.getDrawable(R.drawable.ic_close_white))
        }

        contactId = intent.getStringExtra(EXTRA_CONTACT_ID)
        instantiateDepenencies()

        if (contactId == null) {
            supportActionBar?.title = "Add contact"
        } else {
            edit_mode = true
            supportActionBar?.title = "Edit contact"

            presenter.requestContact(contactId as String)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuLayout = if (edit_mode)
            R.menu.menu_edit
        else {
            R.menu.menu_add_edit
        }

        menuInflater.inflate(menuLayout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_save -> {
                saveButtonClickListener()
            }
            R.id.action_delete -> {
                deleteButtonClickListener()
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun instantiateDepenencies() {
        repository = LocalRepository(Realm.getDefaultInstance())
        interactor = AddEditInteractor(repository)
        presenter = AddEditPresenter(this, interactor)
    }

    override fun showContact(contact: Contact) {
        firstName.setText(contact.firstName, TextView.BufferType.EDITABLE)
        lastName.setText(contact.lastName, TextView.BufferType.EDITABLE)

        val phoneNumberValues = ArrayList<String>()
        contact.phonenumbers.mapTo(phoneNumberValues) { it.number }

        val addressesValues = ArrayList<String>()
        contact.adresses.mapTo(addressesValues) { it.address }

        val emailValues = ArrayList<String>()
        contact.emails.mapTo(emailValues) { it.email }

        // Set field values
        phoneFields.setFieldValues(phoneNumberValues)

        // As address value can be empty
        if (addressesValues.isNotEmpty())
            addressFields.setFieldValues(addressesValues)

        emailsFields.setFieldValues(emailValues)

        if (contact.birthday.isNotEmpty())
            birthdayField.setText(contact.birthday, TextView.BufferType.EDITABLE)
    }

    override fun saveButtonClickListener() {
        presenter.onSaveClicked()
    }

    override fun deleteButtonClickListener() {
        contactId?.let {
            presenter.onDeleteClicked(it)
        }
    }

    override fun getFirstNameValue(): String {
        return firstName.text.toString()
    }

    override fun getLastNameValue(): String {
        return lastName.text.toString()
    }

    override fun getPhoneValues(): ArrayList<String> {
        return phoneFields.getFieldValues()
    }

    override fun getEmailValues(): ArrayList<String> {
        return emailsFields.getFieldValues()
    }

    override fun getAddressValues(): ArrayList<String> {
        return addressFields.getFieldValues()
    }

    override fun getBirthDayValue(): String {
        return birthdayField.text.toString()
    }

    override fun showFirstNameError() {
        firstName.error = resources.getString(R.string.addEdit_input_empty_error)
    }

    override fun hideFirstNameError() {
        firstName.error = null
    }

    override fun showContactSaved() {
        closeView()
    }

    override fun closeView() {
            val intent = Intent(this, ListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
    }
}
