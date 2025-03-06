package com.example.chapeter6

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.setPadding
import com.example.chapeter6.databinding.ActivityMainBinding
import com.example.chapeter6.databinding.DialogCountdownSettingBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countdownSecond = 5
    private var currentCountdownDeciSecond = countdownSecond * 10
    private var currentDeciSecond = 0
    private var timer: Timer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.countdownTextView.setOnClickListener {
            showCountdownSettingDialog()
        }

        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.stopButton.isVisible = false
            binding.pauseButton.isVisible = true
            binding.lapButton.isVisible = true
        }
        binding.stopButton.setOnClickListener {
            showAlertDialog()

        }
        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.isVisible = true
            binding.stopButton.isVisible = true
            binding.pauseButton.isVisible = false
            binding.lapButton.isVisible = false
        }
        binding.lapButton.setOnClickListener {
            lap()
        }
        initCountdownViews()
    }

    private fun initCountdownViews() {
        binding.countdownTextView.text = String.format("%02d", countdownSecond)
        binding.countdownProgressBar.progress = 100
    }

    private fun start() {
        timer = timer(initialDelay = 0, period = 100) {
            if (currentCountdownDeciSecond == 0) {
                currentDeciSecond += 1
                val minutes = currentDeciSecond.div(10) / 60
                val seconds = currentDeciSecond.div(10) % 60
                val deciSeconds = currentDeciSecond % 10

                runOnUiThread {
                    binding.countdownTextView.text = String.format("%02d", countdownSecond)
                    binding.timeTextView.text = String.format("%02d:%02d", minutes, seconds)
                    binding.tickTextView.text = deciSeconds.toString()

                    binding.countdownGroup.isVisible = false
                }
            } else {
                currentCountdownDeciSecond -= 1
                val seconds = currentCountdownDeciSecond / 10
                val progress = (currentCountdownDeciSecond / (countdownSecond * 10f)) * 100

                binding.root.post {
                    binding.countdownProgressBar.progress = progress.toInt()
                    binding.countdownTextView.text = String.format("%02d", seconds)
                }

            }
            if (currentDeciSecond == 0 && currentCountdownDeciSecond < 31 && currentCountdownDeciSecond % 10 == 0) {
                val toneType =
                    if (currentCountdownDeciSecond == 0) ToneGenerator.TONE_CDMA_HIGH_L else ToneGenerator.TONE_CDMA_ANSWER
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME).startTone(
                    toneType, 100
                )
            }

        }
    }

    private fun stop() {
        binding.startButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.pauseButton.isVisible = false
        binding.lapButton.isVisible = false

        currentDeciSecond = 0
        binding.timeTextView.text = "00:00"
        binding.tickTextView.text = "0"
        binding.countdownTextView.text = String.format("%02d", countdownSecond)

        binding.countdownGroup.isVisible = true
        initCountdownViews()
        binding.lapContainerLinearyLayout.removeAllViews()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {
        if (currentDeciSecond == 0) return
        val container = binding.lapContainerLinearyLayout
        val lapTextView = TextView(this).apply {
            textSize = 20f
            gravity = android.view.Gravity.CENTER
            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10
            text = container.childCount.inc().toString() + String.format(
                ". %02d:%02d %01d", minutes, seconds, deciSeconds
            )
            // 1. 01:03 0

            setPadding(30)
        }.let { labTextView ->
            container.addView(labTextView, 0)
        }

    }

    private fun showCountdownSettingDialog() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(dialogBinding.countdownSecondPicker) {
                maxValue = 20
                minValue = 0
                value = countdownSecond
            }
            setView(dialogBinding.root)
            setTitle("카운트다운 설정")
            setPositiveButton("확인") { _, _ ->
                countdownSecond = dialogBinding.countdownSecondPicker.value
                currentCountdownDeciSecond = countdownSecond * 10
                binding.countdownTextView.text = String.format("%02d", countdownSecond)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ -> stop() }
            setNegativeButton("아니요", null)
        }.show()
    }
}