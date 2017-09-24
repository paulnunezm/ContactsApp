package com.nunez.compoundedittext

import android.text.Editable
import android.text.TextWatcher

/**
 * This class is a helper for the EditTexts that
 * notifies if widget has text or not and if has
 * changed it state.
 */
class CustomTextWatcher(
        val listener: (hasText: Boolean, textStateChanged: Boolean) -> (Unit)
) : TextWatcher {

    var previousStateHadText = false
    var currentStateHasText = false

    /**
     * This must be called when the same instance is added as a Watcher
     * for a new EditText. To start with the initial state.
     */
    fun resetWatcher(){
        previousStateHadText = false
        currentStateHasText = false
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(currentText: CharSequence?, p1: Int, p2: Int, count: Int) {

        // Check if the input has text
        currentStateHasText = count > 0

        // Check if the input has changed it state (had or not text)
        if (currentStateHasText == previousStateHadText) {
            listener(currentStateHasText, false)
        } else {
            listener(currentStateHasText, true)
            previousStateHadText = currentStateHasText
        }
    }
}