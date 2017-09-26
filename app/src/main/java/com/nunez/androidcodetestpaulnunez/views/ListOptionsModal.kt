package com.nunez.androidcodetestpaulnunez.views

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nunez.androidcodetestpaulnunez.R
import kotlinx.android.synthetic.main.list_bottom_sheet.*

class ListOptionsModal : BottomSheetDialogFragment() {

    var editClickListener: ((String) -> Unit)? = null
    var onDeleteClickListener: ((String) -> Unit)? = null
    lateinit var id: String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater?.inflate(R.layout.list_bottom_sheet, container, false)

         id = arguments.getString("id")
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        bottom_sheet_edit.setOnClickListener {
            editClickListener?.invoke(id)
        }

        bottom_sheet_delete.setOnClickListener {
            onDeleteClickListener?.invoke(id)
        }
    }

}