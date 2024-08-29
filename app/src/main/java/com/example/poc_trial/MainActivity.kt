package com.example.poc_trial
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnSpeak: Button
    private lateinit var tvSpeechOutput: TextView

    // ActivityResultLauncher for getting the speech input
    private val speechRecognitionLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val speechText = result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            tvSpeechOutput.text = speechText?.get(0) ?: "No speech recognized"
        } else {
            Toast.makeText(this, "Speech recognition failed!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSpeak = findViewById(R.id.btnSpeak)
        tvSpeechOutput = findViewById(R.id.tvSpeechOutput)

        btnSpeak.setOnClickListener {
            startSpeechRecognition()
        }
    }

    private fun startSpeechRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")
        }
        speechRecognitionLauncher.launch(intent)
    }
}
