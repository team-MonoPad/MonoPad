package com.project.monopad.util

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

fun isNotValidName(name : String?): Boolean {
    return name.isNullOrEmpty()
}

fun isNotValidEmail(email : String?): Boolean {
    return email.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isNotValidPassword(password : String?): Boolean {
    val PASSWORD_PATTERN: Pattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{6,24}")
    return password.isNullOrEmpty() || !PASSWORD_PATTERN.matcher(password).matches()
}

fun isNotValidEmailAndPassword(email: String?, password: String?) : Boolean {
    return isNotValidEmail(email) || isNotValidPassword(password)
}

fun isPasswordCheckSuccess(password : String?, password2 : String?) : Boolean {
    return !isNotValidPassword(password) && !isNotValidPassword(password2) && TextUtils.equals(password, password2)
}
