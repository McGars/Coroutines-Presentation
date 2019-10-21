package com.mcgars.coroutines_example.core

import android.view.View
import android.widget.Toast
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_main.*


class Progress (override val containerView: View): LayoutContainer {

    fun show() {
        progress_circular.visibility = View.VISIBLE
        progress_circular_back.visibility = View.VISIBLE
    }

    fun hide() {
        progress_circular.visibility = View.GONE
        progress_circular_back.visibility = View.GONE
    }

    fun showToast(text: Any) {
        Toast.makeText(containerView.context, text.toString(), Toast.LENGTH_SHORT).show()
    }
}