package com.project.monopad

import android.text.TextUtils
import android.util.Patterns
import java.util.regex.Pattern

class LoginPatternCheckUtil {

    companion object {
        fun isValidEmail(email : String?): Boolean {
            return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password : String?): Boolean {
            val PASSWORD_PATTERN: Pattern = Pattern.compile("[a-zA-Z0-9\\!\\@\\#\\$]{6,24}"
            )
            return !TextUtils.isEmpty(password) && PASSWORD_PATTERN.matcher(password).matches()
        }

        fun checkPassword(password : String?, password2 : String?) : Boolean {
            return isValidPassword(password) && isValidPassword(password2) && TextUtils.equals(password, password2)
        }
    }
}