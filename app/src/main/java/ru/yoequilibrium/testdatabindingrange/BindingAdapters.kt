package ru.yoequilibrium.testdatabindingrange

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.google.android.material.slider.RangeSlider


@BindingAdapter("app:valuesAttrChanged")
fun setListeners(
    slider: RangeSlider,
    attrChange: InverseBindingListener
) {
    val listener = RangeSlider.OnChangeListener { _, _, _ -> attrChange.onChange() }
    slider.addOnChangeListener(listener)
}

@InverseBindingAdapter(attribute = "values")
fun getRangeSlider(slider: RangeSlider): List<Float> {
    return slider.values
}

@BindingAdapter("android:values")
fun setSlider(view: RangeSlider, value: List<Float>) {
    if (view.values[0] == value[0] && view.values[1] == view.values[1])
        return
    else
        view.values = value
}

@BindingAdapter("android:text")
fun setText(view: TextView, value: Float?) {
    if (view.text.toString().isEmpty())
        view.text = "0"
    if (view.text.toString().toFloat() == value)
        return
    if (value == null)
        view.text = "0"
    view.text = value?.toInt().toString()
}

@InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
fun getTextString(view: TextView): Float {
    return if (view.text.toString().isEmpty()) {
        view.text = "0"
        0f
    } else {
        view.text.toString().toFloat()
    }
}