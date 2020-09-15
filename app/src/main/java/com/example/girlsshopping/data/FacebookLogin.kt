package com.example.girlsshopping.data

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.example.girlsshopping.R
import com.example.girlsshopping.products.AddProductActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton

class FacebookLogin : FragmentActivity() {
    private var tvfirst_name: TextView? = null
    private val tvlast_namee: TextView? = null
    private val tvfull_name: TextView? = null
    private var tvEmail: TextView? = null
    private var callbackManager: CallbackManager? = null
    var loginButton: LoginButton? = null
    var email: String? = null
    var name: String? = null
    var first_name: String? = null
    var last_name: String? = null
    var accessTokenTracker: AccessTokenTracker? = null
    var accessToken: AccessToken? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook_login)
        callbackManager = CallbackManager.Factory.create()
        tvfirst_name = findViewById(R.id.full_name)
        tvEmail = findViewById(R.id.email)
        loginButton = findViewById<View>(R.id.login_button) as LoginButton
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton!!.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {
                // App code
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                val intent = Intent(applicationContext, AddProductActivity::class.java)
                startActivity(intent)
            }
        })


        ////////////////////////////////////////
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private const val EMAIL = "email"
    }
}