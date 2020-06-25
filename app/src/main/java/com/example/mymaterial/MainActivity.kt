package com.example.mymaterial

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.mymaterial.eliza.Eliza
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_chat.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    private lateinit var recognizer: SpeechRecognizerManager
    private lateinit var ttsManager: TextToSpeechManager
    private var isRecording = false
    private var secondResult = false

    private lateinit var initial: ArrayList<String>
    private lateinit var finl: ArrayList<String>
    private lateinit var reply: ArrayList<String>
    private lateinit var chatList: ArrayList<Message>
    private lateinit var adapter: ChatAdapter
    private var isChating = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initEliza()
        initAcapone()
        initRecognizer()
        setListen()
    }

    override fun onDestroy() {
        stopTTS()
        stopRecognizer()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (isChating) {
            isChating = false
            stopTTS()
            content_main.visibility = View.VISIBLE
            content_chat.visibility = View.GONE
        } else
            super.onBackPressed()
    }

    private fun setListen() {
        bottomAppBar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.menuSetting -> Toast.makeText(this, "設定", Toast.LENGTH_SHORT).show()
                R.id.menuAbout -> Toast.makeText(this, "關於這個 APP", Toast.LENGTH_SHORT).show()
            }
            return@setOnMenuItemClickListener true
        }

        btn_record.setOnClickListener {
            stopTTS()

            if (requestPermission(android.Manifest.permission.RECORD_AUDIO))
                if (!isRecording) {
                    recognizer.start("zh-tw")
                    isRecording = true
                } else {
                    recognizer.stop()
                    isRecording = false
                }
        }

        ll_plan.setOnClickListener {
            startActivity(Intent(this, PlanActivity::class.java))
        }

        ll_review.setOnClickListener {
            startActivity(Intent(this, ReviewActivity::class.java))
        }

        ll_learn.setOnClickListener {
            startActivity(Intent(this, LearnActivity::class.java))
        }

        ll_company.setOnClickListener {
            isChating = true
            content_main.visibility = View.GONE
            content_chat.visibility = View.VISIBLE

            val text = initial.random()
            val msg = Message(0, text)

            speakText("zh-tw", text)
            chatList = arrayListOf(msg)

            adapter = ChatAdapter(this, chatList)
            listview.adapter = adapter
        }
    }

    private fun initEliza() {
        val eliza = Eliza(resources.openRawResource(R.raw.script))
        speakText("zh-tw", eliza.initial)

        eliza.finished()
    }

    private fun initAcapone() {
        initial = arrayListOf()
        finl = arrayListOf()
        reply = arrayListOf()
        val isr = InputStreamReader(resources.openRawResource(R.raw.script2))
        val br = BufferedReader(isr)
        var s = br.readLine()

        try {
            while (s != null) {
                s = br.readLine()

                when {
                    s.startsWith("initial: ") -> initial.add(s.removePrefix("initial: "))
                    s.startsWith("final: ") -> finl.add(s.removePrefix("final: "))
                    s.startsWith("reply: ") -> reply.add(s.removePrefix("reply: "))
                }
            }
        } catch (ignored: Exception) {
        }
    }

    private fun initRecognizer() {
        recognizer = SpeechRecognizerManager(this, object : SpeechRecognizerManager.SpeechRecognizerListener {
            override fun onReadyForSpeech() {
                btn_record?.setImageResource(R.drawable.ic_pause)
                btn_record?.alphaAnimation(true, 1.0f, 0.3f, 1250)
                loading()
            }

            override fun onEndOfSpeech() {
                btn_record?.setImageResource(R.drawable.ic_mic)
                btn_record?.alphaAnimation(false)

                secondResult = false
                isRecording = false
                dismissLoading()
            }

            override fun onError() {
                btn_record?.setImageResource(R.drawable.ic_mic)
                btn_record?.alphaAnimation(false)

                isRecording = false
                dismissLoading()
            }

            override fun onResults(answer: String) {
                if (secondResult) return
                secondResult = true

                with(answer) {
                    val text = when {
                        contains("學習規劃") -> "正在進行規劃"
                        contains("課程學習") -> "請問想學習什麼呢"
                        contains("陪伴") -> "請等我一下"
                        contains(arrayOf("成果", "評量")) -> "正在統計學習的成果"
                        contains(arrayOf("謝謝你", "謝謝您")) -> "你太客氣了"
                        contains(arrayOf("掰掰", "再見", "拜拜")) -> finl.random()
                        else -> reply.random()
                    }

                    speakText("zh-tw", text)

                    if (isChating) {
                        val myMsg = Message(1, answer)
                        val sysMsg = Message(0, text)

                        chatList.add(myMsg)
                        chatList.add(sysMsg)
                        adapter.notifyDataSetChanged()

                        listview.post {
                            val lastVisible = listview.lastVisiblePosition
                            val scrollDown = adapter.count > lastVisible

                            if (scrollDown)
                                listview.setSelection(adapter.count)
                        }
                    }
                }
            }
        })
    }

    private fun speakText(language: String, text: String, rate: Float = 0.8f) {
        if (!::ttsManager.isInitialized)
            ttsManager = TextToSpeechManager(this, object : TextToSpeechManager.SpeakerListener {
                override fun initSuccess() {
                    ttsManager.setSpeechRate(rate)
                    ttsManager.setLanguage(language)
                    ttsManager.speakText(text)
                }
            })
        else {
            ttsManager.setSpeechRate(rate)
            ttsManager.setLanguage(language)
            ttsManager.speakText(text)
        }
    }

    private fun stopTTS() {
        try {
            if (::ttsManager.isInitialized && ttsManager.isSpeaking)
                ttsManager.stopSpeaking()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun stopRecognizer() {
        if (isRecording) {
            isRecording = false
            recognizer.cancel()
            initRecognizer()
            dismissLoading()
            btn_record.setImageResource(R.drawable.ic_mic)
            btn_record.alphaAnimation(false)
        }
    }

    private fun loading() {
        load.visibility = View.VISIBLE

        load.scale = 1.5f
        load.playAnimation()
    }

    private fun dismissLoading() {
        load.visibility = View.GONE
        load.cancelAnimation()
    }
}
