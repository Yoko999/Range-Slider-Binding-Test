package ru.yoequilibrium.testdatabindingrange

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import ru.yoequilibrium.testdatabindingrange.databinding.ActivityMainBinding
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    val filterParams = ObservableField<ProductsFilterParams>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding:ActivityMainBinding =
            DataBindingUtil.setContentView(this,R.layout.activity_main)

        setFilterParams()

        binding.lifecycleOwner = this
        binding.vm = SliderViewModel(filterParams.get()!!)
        binding.btnClick.setOnClickListener {
            val pr = filterParams.get()?.selectedPrice?.get()!!
            Toast.makeText(this,"Установлена цена [" +
                    "${pr[0]}, ${pr[1]}]",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setFilterParams(){
        filterParams.set(ProductsFilterParams().apply {
            minPrice = 10f
            maxPrice = 5000f
            val curMin:Float = selectedPrice.get()!![0]!!
            val curMax:Float = selectedPrice.get()!![1]!!
            selectedPrice.set(listOf(
                max(minPrice,curMin),
                min(maxPrice,curMax)
            ))
        })
    }
}