package com.sunhurov.common.extension

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

/**
 * Created by Yurii Sunhurov on 18.08.2020
 */
/* We create a custom class called MultiTextWatcher.
 * And pass the interface here
 */
class MultiTextWatcher {
    private var callback: TextWatcherWithInstance? = null
    fun setCallback(callback: TextWatcherWithInstance?): MultiTextWatcher {
        this.callback = callback
        return this
    }

    fun registerEditText(editText: EditText): MultiTextWatcher {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                callback?.onTextChanged(s)
            }

            override fun afterTextChanged(editable: Editable) {}
        })
        return this
    }
}


interface TextWatcherWithInstance {
    fun onTextChanged(s: CharSequence?)
}