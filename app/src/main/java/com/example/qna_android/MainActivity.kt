package com.example.qna_android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.widget.TextView
import java.util.Locale

class MainActivity : Activity(), TextToSpeech.OnInitListener {
    private lateinit var tts: TextToSpeech
    private lateinit var recognizer: SpeechRecognizer
    private lateinit var statusView: TextView
    private val questions = arrayOf(
        "איך קוראים לך?",
        "בן כמה אתה?",
        "מה שלומך היום?"
    )
    private val answers = mutableListOf<String>()
    private var currentQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusView = findViewById(R.id.statusView)

        tts = TextToSpeech(this, this)
        recognizer = SpeechRecognizer.createSpeechRecognizer(this)
        recognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onError(error: Int) {
                statusView.text = "שגיאה בזיהוי: $error"
                askNextQuestion()
            }
            override fun onResults(results: Bundle) {
                val data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                val answer = data?.firstOrNull() ?: ""
                answers.add(answer)
                askNextQuestion()
            }
            override fun onPartialResults(partialResults: Bundle) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })
    }

    override fun onInit(status: Int) {
        tts.language = Locale("he", "IL")
        askNextQuestion()
    }

    private fun askNextQuestion() {
        if (currentQuestion >= questions.size) {
            finishConversation()
            return
        }
        val question = questions[currentQuestion]
        statusView.text = question
        tts.speak(question, TextToSpeech.QUEUE_FLUSH, null, "question")
        currentQuestion++
        startListening()
    }

    private fun startListening() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "he-IL")
        recognizer.startListening(intent)
    }

    private fun finishConversation() {
        val transcript = StringBuilder()
        questions.forEachIndexed { i, q ->
            transcript.append(q).append("\n")
            transcript.append(answers.getOrNull(i) ?: "").append("\n\n")
        }
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = android.net.Uri.parse("mailto:alonborn@gmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "תמליל שיחה")
            putExtra(Intent.EXTRA_TEXT, transcript.toString())
        }
        startActivity(Intent.createChooser(emailIntent, "Send Email"))
        statusView.text = "תודה" // Thanks
    }
}
