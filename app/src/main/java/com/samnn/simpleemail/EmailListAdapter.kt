package com.samnn.simpleemail

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EmailListAdapter(private val emails: ArrayList<Email>) : BaseAdapter() {
    override fun getCount() = emails.size

    override fun getItem(p: Int) = emails[p]

    override fun getItemId(p: Int) = p.toLong()

    override fun getView(p: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.email_item, parent, false)

            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        }
        else {
            itemView = convertView
            viewHolder = itemView.tag as ViewHolder
        }

        val email = emails[p]
        viewHolder.sender.text = email.sender
        viewHolder.subject.text = email.subject
        viewHolder.content.text = email.content
        viewHolder.time.text = formatTime(email.time)

        viewHolder.star.isChecked = email.isStarred
        viewHolder.star.setOnClickListener {
            emails[p].isStarred = (it as CheckBox).isChecked
            notifyDataSetChanged()
        }

        viewHolder.important.isChecked = email.isImportant
        viewHolder.important.setOnClickListener {
            emails[p].isImportant = (it as CheckBox).isChecked
            notifyDataSetChanged()
        }

        viewHolder.avatar.text = email.sender[0].uppercase()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            viewHolder.avatarTheme.setBackgroundColor(email.avatarColor)
        }

        return itemView
    }

    private fun formatTime(date: Date): String {
        val now = Calendar.getInstance()
        val pattern: String =
        if (date.year != now.time.year) {
            "dd/MM/yy"
        }
        else if (date.date != now.time.date) {
            "dd MMM"
        }
        else {
            "h:mm a"
        }
        val format = SimpleDateFormat(pattern, Locale("vi", "VN"))
        return format.format(date)
    }

    class ViewHolder(itemView: View) {
        val sender: TextView
        val subject: TextView
        val content: TextView
        val time: TextView
        val star: CheckBox
        val important: CheckBox
        val avatar: TextView
        val avatarTheme: FrameLayout
        init {
            sender = itemView.findViewById(R.id.source)
            subject = itemView.findViewById(R.id.subject)
            content = itemView.findViewById(R.id.content)
            time = itemView.findViewById(R.id.time)
            star = itemView.findViewById(R.id.star)
            important = itemView.findViewById(R.id.important)
            avatar = itemView.findViewById(R.id.avatar)
            avatarTheme = itemView.findViewById(R.id.avatar_frame)
        }
    }

}