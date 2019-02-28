package ru.geekbrains.kotlin1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var count : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnTest = findViewById<Button>(R.id.btn_test)
        var txtView = findViewById<TextView>(R.id.txt_view)
        btnTest.setOnClickListener {
            txtView.text = "Кнопка нажата ${count++}"
        }
    }
}
