package ru.yoequilibrium.testdatabindingrange

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by stanislav at 24.11.21
 */
class SliderViewModel(val params: ProductsFilterParams) : ViewModel() {

    val rangeMin = params.minPrice
    val rangeMax = params.maxPrice

    private val selectedRangeMin = params.selectedPrice.get()!![0]
    private val selectedRangeMax = params.selectedPrice.get()!![1]

    val floatingRangeMin = MutableLiveData(selectedRangeMin!!)
    val floatingRangeMax = MutableLiveData(selectedRangeMax!!)
    var sliderRange = MutableLiveData<List<Float?>>().apply { value = listOf(selectedRangeMin, selectedRangeMax) }

    init {
        floatingRangeMin.observeForever {
            if (it in rangeMin..rangeMax)
                if (floatingRangeMin.value!! < floatingRangeMax.value!!)
                    sliderRange.value = listOf(floatingRangeMin.value, sliderRange.value?.get(1))
                else
                    sliderRange.value = listOf(floatingRangeMin.value, floatingRangeMin.value)

        }
        floatingRangeMax.observeForever {
            if (it in rangeMin..rangeMax)
                if (floatingRangeMin.value!! < floatingRangeMax.value!!)
                    sliderRange.value = listOf(sliderRange.value?.get(0), floatingRangeMax.value)
                else
                    sliderRange.value = listOf(floatingRangeMax.value, floatingRangeMax.value)
        }

        sliderRange.observeForever {
            params.selectedPrice.set(listOf(it[0],it[1]))
            /*facet.values[1].selected_value = it[0].toString()
            facet.values[0].selected_value = it[1].toString()*/
            //без этой проверки зацикливается
            if (it[0] != floatingRangeMin.value || it[1] != floatingRangeMax.value) {
                floatingRangeMin.value = it[0]
                floatingRangeMax.value = it[1]
            }

            Log.d("MY","Slider range observe values from " +
                    "{$floatingRangeMin,$floatingRangeMax} to {$it[0],$it[1]}")
        }
    }


    class SliderVMFactory(val params: ProductsFilterParams) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SliderViewModel(params) as T
        }
    }
}


