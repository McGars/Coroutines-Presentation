package com.mcgars.coroutines_example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.mcgars.coroutines_example.core.Progress
import com.mcgars.coroutines_example.feature.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val progress by lazy { Progress(root) }

    private val examples: Array<ExampleRunnuble> by lazy {
        arrayOf(
            ExampleCoroutine1(progress),
            ExampleRxJava1(progress),
            ExampleCoroutine2(progress),
            ExampleRxJava2(progress),
            ExampleCoroutine3(progress),
            ExampleRxJava3(progress),
            ExampleCoroutine4(progress),
            ExampleRxJava4(progress),
            ExampleCoroutine5(progress),
            ExampleRxJava5(progress),
            ExampleCoroutine6(progress),
            ExampleCoroutine7(progress)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fillButtons()

    }

    private fun fillButtons() {
        examples.forEachIndexed { index, example ->
            addButton(index, example.name())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun addButton(index: Int, name: String) {
        container.addView(AppCompatButton(this).apply {
            text = "${index + 1}. $name"
            gravity = Gravity.LEFT or Gravity.CENTER_VERTICAL
            setOnClickListener { handleButtonClick(index) }
        })
    }

    private fun handleButtonClick(index: Int) {
        examples[index].run()
    }

}
