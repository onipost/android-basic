package com.example.com.presentation.utils

import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter

@BindingAdapter("visibilityGone")
fun View.visibilityGone(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("onClick")
fun View.onClick(action: () -> Unit) {
    setOnClickListener { action.invoke() }
}

@BindingAdapter("menuRes", "menuHandler", requireAll = true)
fun View.bindMenu(menuRes: Int, menuHandler: PopupMenu.OnMenuItemClickListener) {
    setOnClickListener {
        val popup = PopupMenu(context, this)
        popup.inflate(menuRes)
        popup.setOnMenuItemClickListener(menuHandler)
        popup.show()
    }
}

@BindingAdapter("textID")
fun TextView.setTextFromStringID(stringID: Int) {
    if (stringID > 0) setText(stringID)
}

@BindingAdapter("linkMovementMethod")
fun TextView.setMovementMethod(value: Boolean) {
    if (value) movementMethod = LinkMovementMethod.getInstance()
}
@BindingAdapter("onSwitch")
fun SwitchCompat.onSwitch(action: (Boolean) -> Unit) {
    setOnCheckedChangeListener { buttonView, isChecked ->
        if (buttonView.isPressed) action.invoke(isChecked)
    }
}

@BindingAdapter("android:src")
fun ImageView.setImage(icon: Int) {
    setImageResource(icon)
}
