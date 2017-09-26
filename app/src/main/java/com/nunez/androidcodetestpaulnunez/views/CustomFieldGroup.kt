package com.nunez.compoundedittext

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.nunez.androidcodetestpaulnunez.R
import kotlinx.android.synthetic.main.custom_field_group.view.*


class CustomFieldGroup @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val INPUT_TEXT = 1
        const val INPUT_PHONE_NUMBER = 2
    }

    private var fieldsArray = ArrayList<CustomField>()
    private lateinit var textWatcher: CustomTextWatcher
    private var imageDrawable: Drawable
    private var hint = ""
    private var errorMessage = ""
    private var inputType = 0
    private var mustHaveAtLeastOneFilled = false


    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_field_group, this)

        addNewBtn.visibility = View.GONE

        addNewBtn.setOnClickListener { addNewField(true) }

        // Check for the added parameters
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomFieldGroup)
        hint = typedArray.getString(R.styleable.CustomFieldGroup_input_hint)
        imageDrawable = typedArray.getDrawable(R.styleable.CustomFieldGroup_reference_image)
        inputType = typedArray.getInt(R.styleable.CustomFieldGroup_input_type, 0)

        val error = typedArray.getString(R.styleable.CustomFieldGroup_input_error_message)
        errorMessage = error ?: ""
        mustHaveAtLeastOneFilled = typedArray.getBoolean(R.styleable.CustomFieldGroup_input_has_at_least_one, false)
        typedArray.recycle()

        // Add the reference image
        image.setImageDrawable(imageDrawable)

        // Add the first field
        initTexWatcherListener()
        addNewField()
    }

    private fun showAddButton() {
        addNewBtn.visibility = View.VISIBLE
        addNewBtn.alpha = 0f
        val animation = ObjectAnimator.ofFloat(addNewBtn, "alpha", 1f)
        animation.duration = 500
        animation.start()
    }

    private fun hideAddButton() {
        addNewBtn.visibility = View.GONE
    }

    private fun addNewField(requestFocus: Boolean = false) {
        hideAddButton()

        // Create a new field
        val field = CustomField(context)
        if (hint.isNotEmpty()) field.setHint(hint)

        // Set input type
        val input = if (inputType == INPUT_PHONE_NUMBER)
            CustomField.INPUT_PHONE_NUMBER
        else
            CustomField.INPUT_TEXT

        field.setInputType(input)

        // Set the clear handler
        field.setClearButtonHandler { clearButtonHandler(it) }

        // Add it to the layout
        fields.addView(field)

        // Add it to the collection
        fieldsArray.add(field)

        setListenerToTheLastVisibleField()

        // Set focus to the new field if is not the first
        if (fieldsArray.size > 1 && requestFocus) field.requestFocus()
    }

    private fun initTexWatcherListener() {
        textWatcher = CustomTextWatcher {
            hasText, textStateChanged ->

            if (textStateChanged) {
                if (hasText)
                    showAddButton()
                else
                    hideAddButton()
            }
        }
    }

    private fun clearButtonHandler(field: CustomField) {
        if (fieldsArray.size > 1) {
            fields.removeView(field)
            fieldsArray.remove(field)
        } else {
            field.clearText()
            // Assure that the add button isn't showing when only having one empty field
            if (fieldsArray.size == 1)
                hideAddButton()
        }

        setListenerToTheLastVisibleField()
    }

    private fun setListenerToTheLastVisibleField() {
        if (fieldsArray.size == 1) {
            fieldsArray[0].setTextListener(textWatcher)
        } else {
            textWatcher.resetWatcher()
            fieldsArray[fieldsArray.size - 2].removeTextListener()
            fieldsArray[fieldsArray.size - 1].setTextListener(textWatcher)
        }
    }

    fun setFieldValues(values: List<String>) {
        for (i in 0..values.size - 1) {

            // check if the field exists if not add a new
            if (fieldsArray.size - 1 < i)
                addNewField()

            fieldsArray[i].setText(values[i])
        }
    }

    fun getFieldValues(): ArrayList<String> {
        val values = ArrayList<String>()

        if (values.size == 0 && fieldsArray[0].getFieldValue().isEmpty()) {
            if(mustHaveAtLeastOneFilled)
                fieldsArray[0].setError(errorMessage)
        } else {
            fieldsArray.filter {
                it.getFieldValue().isNotEmpty()
            }.mapTo(values) {
                it.getFieldValue()
            }
        }

        return values
    }
}
