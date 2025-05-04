package com.example.datepicker

import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var datePicker: DatePicker
    private lateinit var btnGetDate: Button
    private lateinit var tvSelectedDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        datePicker = findViewById(R.id.datePicker)
        btnGetDate = findViewById(R.id.btnGetDate)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)

        // 날짜 초기화 (선택적)
        datePicker.updateDate(2025, 3, 27) // 2025년 4월 27일 (월은 0부터 시작)

        // 날짜 선택 리스너
        datePicker.setOnDateChangedListener { view, year, monthOfYear, dayOfMonth ->
            val selectedDate = "${year}년 ${monthOfYear + 1}월 ${dayOfMonth}일"
            tvSelectedDate.text = "날짜 변경 이벤트: $selectedDate"
        }

        btnGetDate.setOnClickListener {
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1 // 월은 0부터 시작하므로 +1
            val year = datePicker.year

            val selectedDate = "${year}년 ${month}월 ${day}일"
            tvSelectedDate.text = "선택된 날짜: $selectedDate"
        }
    }
}