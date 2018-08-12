package me.worric.kotlinplayground

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val person = Person(name = "John", surname = "Smith")
        message.text = person.getNameFullName()
        toast(message = person.getNameFullName())
    }

    fun toast(message: String,
              length: Int = Toast.LENGTH_SHORT,
              tag: String = MainActivity::class.java.simpleName) {
        Toast.makeText(this, "[$tag] $message", length).show()
    }

}
