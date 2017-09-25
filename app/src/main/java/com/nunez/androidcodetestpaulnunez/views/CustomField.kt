package com.nunez.compoundedittext

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.nunez.androidcodetestpaulnunez.R
import kotlinx.android.synthetic.main.custom_field.view.*


class CustomField @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr){

    companion object {
        const val INPUT_PHONE_NUMBER = 0
        const val INPUT_TEXT = 1
    }

    lateinit var textWatcher: CustomTextWatcher
    var clearHandlerSet = false

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_field, this)

        setClearListener()
    }

    fun setTextListener(textWatcher: CustomTextWatcher) {
        this.textWatcher = textWatcher
        customField_editText.addTextChangedListener(this.textWatcher)
    }

    fun setText(value: String){
        customField_editText.setText(value, TextView.BufferType.EDITABLE)
    }

    fun removeTextListener(){
        customField_editText.removeTextChangedListener(textWatcher)
    }

    fun setHint(hint: String) {
        customField_editText.hint = hint
    }

    fun setClearListener(){
        // Clear the text when the x is clicked
        customField_clear.setOnClickListener {
            if(!clearHandlerSet) clearText()
        }
    }

    fun clearText(){
        customField_editText.setText("")
    }

    fun setInputType(type: Int){
        customField_editText.inputType = when(type){
            INPUT_PHONE_NUMBER -> InputType.TYPE_CLASS_PHONE
            INPUT_TEXT -> InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
            else -> InputType.TYPE_CLASS_TEXT
        }
    }

    fun setClearButtonHandler(listener: (CustomField) -> (Unit)){
        clearHandlerSet = true
        customField_clear.setOnClickListener {
            listener(this)
        }
    }

    fun getFieldValue(): String = customField_editText.text.toString()

    fun setError(errorMessage: String) {
        customField_editText.error = errorMessage
    }
}
