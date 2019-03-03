package com.clearcommon.helper.custom

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.bazmashop.R
import com.clearcommon.helper.util.isEmailValid

open class CustomTextWatcher(
    val context: Context,
    val editText: EditText,
    val textWatcherType: TextWatcherType
) :
    TextWatcher {
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        when (textWatcherType) {
            TextWatcherType.EMAIL -> {
                if (editText.text.toString().isEmailValid())
                    editText.setTextColor(context.resources.getColor(R.color.black_light))
                else
                    editText.setTextColor(context.resources.getColor(R.color.red_error_text))
            }
        }
    }

    override fun afterTextChanged(s: Editable?) {
    }

    enum class TextWatcherType {
        EMAIL
    }
}
