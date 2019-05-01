package development.dreamcatcher.muzmatchlight.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import development.dreamcatcher.muzmatchlight.R


class CustomEditText : AppCompatEditText {

    internal var sendButtonImage: Drawable? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

        // Initialize Drawable member variable.
        sendButtonImage = ResourcesCompat.getDrawable(resources,
                R.drawable.blue_arrow_button, null)

        showSendButton()

        // If the Send button is tapped, send the message
        setOnTouchListener(OnTouchListener { v, event ->

            if (getCompoundDrawablesRelative()[2] != null) {

                val clearButtonStart: Float
                val clearButtonEnd: Float
                var isClearButtonClicked = false

                if (getLayoutDirection() === LAYOUT_DIRECTION_RTL) {
                    clearButtonEnd = (sendButtonImage!!.intrinsicWidth + getPaddingStart()).toFloat()
                    if (event.x < clearButtonEnd)
                        isClearButtonClicked = true

                } else {
                    clearButtonStart = (getWidth() - getPaddingEnd() - sendButtonImage!!.intrinsicWidth).toFloat()
                    if (event.x > clearButtonStart)
                        isClearButtonClicked = true

                }

                if (isClearButtonClicked) {

                    if (event.action == MotionEvent.ACTION_DOWN) {

                        // Switch to the faded version of Send button.
                        sendButtonImage?.alpha = 50
                    }

                    if (event.action == MotionEvent.ACTION_UP) {
                        // Switch to the coloured version of Send button.
                        sendButtonImage?.alpha = 255
                        return@OnTouchListener true
                    }
                } else {
                    return@OnTouchListener false
                }
            }
            false
        })

        // If the text changes, show or hide the Send button.
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence,
                                           start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence,
                                       start: Int, before: Int, count: Int) {
                //showSendButton()
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

    private fun showSendButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, sendButtonImage, null)
    }
}