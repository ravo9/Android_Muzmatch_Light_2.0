package development.dreamcatcher.muzmatchlight.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import development.dreamcatcher.muzmatchlight.R
import android.view.inputmethod.BaseInputConnection



class CustomEditText : AppCompatEditText {

    private var sendButtonImage: Drawable? = null

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

        setCompoundDrawablesRelativeWithIntrinsicBounds(
                null, null, sendButtonImage, null)

        // Initialize touch listener for the Send button
        setOnTouchListener(OnTouchListener { _, event ->

            if (compoundDrawablesRelative[2] != null) {

                // Check if clicked area belongs to the Send button
                val clearButtonStart: Float
                val clearButtonEnd: Float
                var isSendButtonClicked = false

                if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                    clearButtonEnd = (sendButtonImage!!.intrinsicWidth + paddingStart).toFloat()
                    if (event.x < clearButtonEnd) {
                        isSendButtonClicked = true
                    }
                } else {
                    clearButtonStart = (width - paddingEnd - sendButtonImage!!.intrinsicWidth).toFloat()
                    if (event.x > clearButtonStart) {
                        isSendButtonClicked = true
                    }
                }

                // If the Send button is tapped, send the message
                if (isSendButtonClicked) {

                    if (event.action == MotionEvent.ACTION_DOWN) {

                        // Perform 'Send' action
                        val inputConnection = BaseInputConnection(this, true)
                        inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))

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
    }
}