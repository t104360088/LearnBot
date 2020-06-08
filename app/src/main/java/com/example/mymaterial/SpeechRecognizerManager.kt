package com.example.mymaterial

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast

class SpeechRecognizerManager(val context: Context, listener: SpeechRecognizerListener) {

    private var recognizer: SpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)

    init {
        if (isAvailable())
            recognizer.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(p0: Bundle?) {
                    listener.onReadyForSpeech()
                }

                override fun onBeginningOfSpeech() {}

                override fun onBufferReceived(p0: ByteArray?) {}

                override fun onRmsChanged(rmsdB: Float) {}

                override fun onPartialResults(p0: Bundle?) {}

                override fun onEvent(p0: Int, p1: Bundle?) {}

                override fun onEndOfSpeech() {
                    listener.onEndOfSpeech()
                }

                override fun onError(errorCode: Int) {
                    val errorMessage = getErrorText(errorCode)
                    Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()

                    listener.onError()
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    matches?.let { listener.onResults(it[0].toLowerCase()) }
                }
            })
    }

    interface SpeechRecognizerListener {
        fun onReadyForSpeech()

        fun onEndOfSpeech()

        fun onError()

        fun onResults(answer: String)
    }

    fun start(language: String) {
        if (isAvailable()) {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
            intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.packageName)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH)
            intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 100000L)

            try {
                recognizer.startListening(intent)
            } catch (e: Exception) {
                Toast.makeText(context, "無法取得辨識服務", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }
        }
    }

    fun stop() { if (isAvailable()) recognizer.stopListening() }

    fun cancel() { if (isAvailable()) recognizer.cancel() }

    fun destroy() { if (isAvailable()) recognizer.destroy() }

    private fun isAvailable(): Boolean {
        val isAvailable = SpeechRecognizer.isRecognitionAvailable(context)
        if (!isAvailable) Toast.makeText(context, "無法取得辨識服務", Toast.LENGTH_SHORT).show()
        return isAvailable
    }

    private fun getErrorText(errorCode: Int): String {
        return when (errorCode) {
            SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
            SpeechRecognizer.ERROR_CLIENT -> "Client side error"
            SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
            SpeechRecognizer.ERROR_NETWORK -> "Network error"
            SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
            SpeechRecognizer.ERROR_NO_MATCH -> "No match"
            SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
            SpeechRecognizer.ERROR_SERVER -> "error from server"
            SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
            else -> "Didn't understand, please try again."
        }
    }
}