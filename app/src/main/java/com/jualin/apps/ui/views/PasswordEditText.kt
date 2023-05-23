package com.jualin.apps.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.jualin.apps.R

class PasswordEditText : AppCompatEditText, View.OnTouchListener {

    private lateinit var visibilityButtonDrawable: Drawable
    private var isVisible = false

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)
        setVisibilityMode()
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (compoundDrawables[2]!=null) {
            var isButtonClicked = false
            val visibilityButtonStart: Float =
                (width - paddingEnd - visibilityButtonDrawable.intrinsicWidth).toFloat()
            when {
                event.x > visibilityButtonStart -> isButtonClicked = true
            }

            if (isButtonClicked) {
                when (event.action) {
                    MotionEvent.ACTION_UP -> {
                        isVisible = !isVisible
                        setVisibilityMode()
                    }
                }
                return true
            }

        }
        return false
    }

    private fun setVisibilityMode() {
        if (isVisible) {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            visibilityButtonDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_baseline_visibility_24
            ) as Drawable
            setButtonDrawables(endOfTheText = visibilityButtonDrawable)
        } else {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            visibilityButtonDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_baseline_visibility_off_24
            ) as Drawable
            setButtonDrawables(endOfTheText = visibilityButtonDrawable)
        }
    }

    private fun setButtonDrawables(
        startOfTheText: Drawable? = null,
        topOfTheText: Drawable? = null,
        endOfTheText: Drawable? = null,
        bottomOfTheText: Drawable? = null
    ) {
        setSelection(text?.length as Int)
        setCompoundDrawablesWithIntrinsicBounds(
            startOfTheText,
            topOfTheText,
            endOfTheText,
            bottomOfTheText
        )
    }
}