package org.philosophicas.mercacaiza

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class HiperLinkTextView(context: Context, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    var colorNormal = Color.BLUE
    var clickedColor = Color.MAGENTA
    private var hiperLinkColor = colorNormal


    init {
        paintFlags += Paint.UNDERLINE_TEXT_FLAG
    }

    override fun setOnClickListener(l: OnClickListener?) {

        val ocl = OnClickListener {
            hiperLinkColor = clickedColor
            setTextColor(hiperLinkColor)
            l?.onClick(this@HiperLinkTextView)
        }



        super.setOnClickListener(ocl)
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        setTextColor(hiperLinkColor)

    }


}