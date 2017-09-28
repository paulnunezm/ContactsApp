package com.nunez.androidcodetestpaulnunez.screens.addEditContact

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.nunez.androidcodetestpaulnunez.R
import com.nunez.androidcodetestpaulnunez.entities.Contact
import com.nunez.androidcodetestpaulnunez.repository.LocalRepository
import com.nunez.androidcodetestpaulnunez.repository.RepositoryContract
import com.nunez.androidcodetestpaulnunez.screens.contactList.ListActivity
import com.nunez.androidcodetestpaulnunez.views.DatePickerFragment
import io.realm.Realm
import kotlinx.android.synthetic.main.add_edit_activity.*
import kotlinx.android.synthetic.main.contact_saved.*
import kotlinx.android.synthetic.main.content_add_edit.*


class AddEditActivity : AppCompatActivity(), AddEditContract.View {

    companion object {
        val LOG_TAG = AddEditActivity::class.java.simpleName.toString()
        const val EXTRA_CONTACT_ID = "contact_id"
    }

    var edit_mode = false
    var contactId: String? = null
    var menu: Menu? = null

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

        birthdayField.setOnFocusChangeListener { view, b ->
            if (b)
                birthdayClickListener()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuLayout = if (edit_mode)
            R.menu.menu_edit
        else {
            R.menu.menu_add_edit
        }

        this.menu = menu

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

    override fun birthdayClickListener() {
        val datePicker = DatePickerFragment()

        datePicker.apply {
            setDateSelecterListener {
                findViewById<EditText>(R.id.birthdayField).setText(it, TextView.BufferType.EDITABLE)
            }
            show(supportFragmentManager, "datePicker")
        }

    }

    override fun showFirstNameError() {
        firstName.error = resources.getString(R.string.addEdit_input_empty_error)
    }

    override fun hideFirstNameError() {
        firstName.error = null
    }

    override fun showContactSaved() {
        runSavedAnimation()
    }

    override fun closeView() {
        val intent = Intent(this, ListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    private fun runSavedAnimation() {
        // get a reference for the save action button
        val saveButtonView = findViewById<View>(R.id.action_save)

        // previously invisible view
        val myView = savedContactMessage

        toolbar.visibility = View.INVISIBLE

        // hide the keyboard
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }

        if (myView != null && saveButtonView != null) {
            // get the center for the clipping circle
            val cx = container.width - saveButtonView.width / 2
            Log.d(LOG_TAG, "$cx")
            val cy = saveButtonView.measuredHeight / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.max(myView.width, myView.height) / 2

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0f, finalRadius.toFloat())
            anim.interpolator = AccelerateDecelerateInterpolator()

            // make the view visible and start the animation
            myView.visibility = View.VISIBLE
            anim.start()
            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(p0: Animator?) {}
                override fun onAnimationCancel(p0: Animator?) {}
                override fun onAnimationStart(p0: Animator?) {
                }

                override fun onAnimationEnd(p0: Animator?) {
                    val handler = Handler()
                    handler.postDelayed({finish()},1000)
//                    finish() // finish the activity/
                }
            })
        } else {
            finish()
        }
    }
}
