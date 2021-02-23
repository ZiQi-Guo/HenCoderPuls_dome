package com.example.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.app.entity.User
import com.example.app.widget.CodeView
import com.example.core.utils.CacheUtils
import com.example.core.utils.Utils
import com.example.lesson.LessonActivity
import kotlin.reflect.KProperty

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val usernameKey = "username"
    private val passwordKey = "password"
    private lateinit var et_username: EditText
    private lateinit var et_password: EditText
    private lateinit var et_code: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        et_username = findViewById(R.id.et_username)
        et_password = findViewById(R.id.et_password)
        et_code = findViewById(R.id.et_code)
        et_username.setText(CacheUtils[usernameKey])
        et_password.setText(CacheUtils[passwordKey])

        findViewById<Button>(R.id.btn_login).setOnClickListener(this)
        findViewById<CodeView>(R.id.code_view).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v is CodeView) {
            v.updateCode()
        } else if (v is Button) {
            login()
        }
    }

    var usernameSaver: String by Saver(usernameKey)
    var passwordSaver: String by Saver(passwordKey)

    class Saver(private val key: String) {
        operator fun getValue(mainActivity: MainActivity, property: KProperty<*>): String {
            return CacheUtils[key]!!
        }

        operator fun setValue(mainActivity: MainActivity, property: KProperty<*>, value: String) {
            CacheUtils.save(key, value)
        }
    }

    private fun login() {
        val username = et_username.text.toString()
        val password = et_password.text.toString()
        val code = et_code.text.toString()
        val user = User(username, password, code)

        fun verify(): Boolean {
            if (user.username?.length ?: 0 < 4) {
                Utils.toast("用户名不合法")
                return false
            }
            if (user.password?.length ?: 0 < 4) {
                Utils.toast("密码不合法")
                return false
            }
            return true
        }

        if (verify()) {
            //验证通过后保存
            usernameSaver = username
            passwordSaver = password
            startActivity(Intent(this, LessonActivity::class.java))
        }
    }

}