package com.samnn.simpleemail

import java.util.Date

class Email(
    val sender: String,
    val subject: String?,
    val content: String?,
    val time: Date
) {
    var isStarred: Boolean = false
    var isImportant: Boolean = false
    val avatarColor = (Math.random() * 16777215).toInt() or (0xFF shl 24)

    override fun toString(): String {
        return "Email from $sender at ${time.toString()}\nSubject: $subject\nContent: $content\n(${if (isStarred) "Starred" else ""}, ${if (isImportant) "Important" else ""})"
    }
}
