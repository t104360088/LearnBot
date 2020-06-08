package com.example.mymaterial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mymaterial.eliza.Eliza

class ChatActivity : AppCompatActivity() {

//    private lateinit var recognizer: SpeechRecognizerManager
//    private lateinit var ttsManager: TextToSpeechManager
//    private var isRecording = false
//    private var secondResult = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_chat)
//
//
//
//        val e = Eliza(resources.openRawResource(R.raw.script))
//        e.initial
//        e.talk()
//
//        btn_record.setOnClickListener {
//            stopTTS()
//
//            if (requestPermission(android.Manifest.permission.RECORD_AUDIO))
//                if (!isRecording) {
//                    recognizer.start("zh-tw")
//                    isRecording = true
//                } else {
//                    recognizer.stop()
//                    isRecording = false
//                }
//        }
//    }
//
//    private fun initRecognizer() {
//        recognizer = SpeechRecognizerManager(this, object : SpeechRecognizerManager.SpeechRecognizerListener {
//            override fun onReadyForSpeech() {
//                btn_record?.setImageResource(R.drawable.ic_pause)
//                btn_record?.alphaAnimation(true, 1.0f, 0.3f, 1250)
//                loading()
//            }
//
//            override fun onEndOfSpeech() {
//                btn_record?.setImageResource(R.drawable.ic_mic)
//                btn_record?.alphaAnimation(false)
//
//                secondResult = false
//                isRecording = false
//                dismissLoading()
//            }
//
//            override fun onError() {
//                btn_record?.setImageResource(R.drawable.ic_mic)
//                btn_record?.alphaAnimation(false)
//
//                isRecording = false
//                dismissLoading()
//            }
//
//            override fun onResults(answer: String) {
//                if (secondResult) return
//                secondResult = true
//
//                Toast.makeText(this@MainActivity, answer, Toast.LENGTH_SHORT).show()
//
//                with(answer) {
//                    val reply = when {
//                        contains("學習規劃") -> "正在進行規劃"
//                        contains("進行學習") -> "請問想學習什麼呢"
//                        contains("陪伴") -> "請等我一下"
//                        contains(arrayOf("成果", "評量")) -> "正在統計學習的成果"
//                        else -> "很抱歉我不明白你的意思"
//                    }
//
//                    speakText("zh-tw", reply)
//                }
//            }
//        })
//    }
//
//    private fun speakText(language: String, text: String, rate: Float = 0.8f) {
//        if (!::ttsManager.isInitialized)
//            ttsManager = TextToSpeechManager(this, object : TextToSpeechManager.SpeakerListener {
//                override fun initSuccess() {
//                    ttsManager.setSpeechRate(rate)
//                    ttsManager.setLanguage(language)
//                    ttsManager.speakText(text)
//                }
//            })
//        else {
//            ttsManager.setSpeechRate(rate)
//            ttsManager.setLanguage(language)
//            ttsManager.speakText(text)
//        }
//    }
//
//    private fun stopTTS() {
//        try {
//            if (::ttsManager.isInitialized && ttsManager.isSpeaking)
//                ttsManager.stopSpeaking()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun stopRecognizer() {
//        if (isRecording) {
//            isRecording = false
//            recognizer.cancel()
//            initRecognizer()
//            dismissLoading()
//            btn_record.setImageResource(R.drawable.ic_mic)
//            btn_record.alphaAnimation(false)
//        }
//    }
//
//    private fun loading() {
//        load.visibility = View.VISIBLE
//
//        load.scale = 1.5f
//        load.playAnimation()
//    }
//
//    private fun dismissLoading() {
//        load.visibility = View.GONE
//        load.cancelAnimation()
//    }
}
