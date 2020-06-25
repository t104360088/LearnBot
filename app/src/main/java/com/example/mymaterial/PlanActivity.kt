package com.example.mymaterial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_plan.*
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import android.graphics.Bitmap
import com.bumptech.glide.Priority
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.request.RequestOptions



class PlanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plan)

        title = "學習規劃"

        val options = RequestOptions()
            .transform(MultiTransformation<Bitmap>(RoundedCorners(20)))
            .priority(Priority.NORMAL)

        Glide.with(this).load(R.drawable.parent).apply(options).into(img_parent)
        Glide.with(this).load(R.drawable.me).apply(options).into(img_me)
    }
}
