package com.example.mymaterial

import android.content.Context
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ChatAdapter(context: Context, list: ArrayList<Message>):
    ArrayAdapter<Message>(context, 0, list) {

    private lateinit var holder: ViewHolder

    private class ViewHolder {
        lateinit var tv_message: TextView
        var tv_name: TextView? = null
        var img_photo: ImageView? = null
        var circleImageView: ImageView? = null
        var messageLayout: FrameLayout? = null
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.type ?: super.getItemViewType(position)
    }

    override fun getViewTypeCount() = 2

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val itemType = getItemViewType(position)

        if (convertView == null) {
            view = when (itemType) {
                0 -> View.inflate(context, R.layout.item_chatroom_left, null)
                1 -> View.inflate(context, R.layout.item_chatroom_right, null)
                else -> View.inflate(context, R.layout.item_chatroom_left, null)
            }
            holder = ViewHolder()
            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        holder.tv_message = view.findViewById(R.id.tv_message)
        holder.messageLayout = view.findViewById(R.id.messageLayout)

        if (itemType != -1) {
            holder.img_photo = view.findViewById(R.id.img_photo)
        }

        if (itemType == 0) {
            holder.circleImageView = view.findViewById(R.id.circleImageView)
            holder.tv_name = view.findViewById(R.id.tv_name)
        }

        setMsg(position)
        return view
    }

    private fun setMsg(position: Int) {
        holder.img_photo?.visibility = View.GONE

        holder.messageLayout?.visibility = View.VISIBLE
        holder.tv_message.visibility = View.VISIBLE
        holder.tv_message.text = getItem(position)?.msg
        holder.tv_message.movementMethod = LinkMovementMethod.getInstance()
        holder.tv_message.autoLinkMask = Linkify.ALL
        holder.tv_message.setLinkTextColor(context.resources.getColor(R.color.colorPrimaryDark))
    }
}
