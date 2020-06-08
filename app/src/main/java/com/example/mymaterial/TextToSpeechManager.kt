package com.example.mymaterial

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*

class TextToSpeechManager(context: Context, listener: SpeakerListener) {
    private var speaker: TextToSpeech
    val isSpeaking: Boolean
        get() {
            return speaker.isSpeaking
        }

    init {
        speaker = TextToSpeech(context, TextToSpeech.OnInitListener {
            when (it) {
                TextToSpeech.SUCCESS -> {
                    setSpeechRate()
                    listener.initSuccess()
                }
                TextToSpeech.ERROR -> {
                    Toast.makeText(context, "發生錯誤", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    interface SpeakerListener {
        fun initSuccess()
    }

    fun setSpeechRate(speed: Float = 0.8f) {
        speaker.setSpeechRate(speed)
    }

    fun setLanguage(language: String) {
        speaker.language = when {
            language.contains("en") -> Locale.US
            language.contains("ja") -> Locale.JAPANESE
            language.contains("es") -> Locale("es", "ES")
            language.contains("th") -> Locale("th", "TH")
            language.contains("ko") -> Locale.KOREAN
            language.contains("vi") -> Locale("vi", "VN")
            language.contains("de") -> Locale.GERMAN
            language.contains("fr") -> Locale.FRENCH
            language.contains("ru") -> Locale("ru", "RU")
            else -> Locale.TRADITIONAL_CHINESE
        }
    }

    fun speakText(text: String) {
        speaker.speak(text, TextToSpeech.QUEUE_FLUSH,null, "0")
    }

    fun releaseSpeaker() {
        speaker.stop()
        speaker.shutdown()
    }

    fun stopSpeaking() {
        speaker.stop()
    }
}