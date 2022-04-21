package dev.software.bing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dev.software.bing.databinding.ActivityMainBinding
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addFragment(CalculatorFragment())

    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, fragment)
                .addToBackStack(null)
                .commit()
    }
}

fun Fragment.pushFragment(list: ArrayList<String>) {
    (requireActivity() as MainActivity).
    addFragment(ListFragment.getInstance(list))
}