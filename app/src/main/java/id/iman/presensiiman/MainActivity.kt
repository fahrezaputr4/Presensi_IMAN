package id.iman.presensiiman

import android.Manifest.permission
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 17

    @SuppressLint("InlinedApi")
    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
        } else {
            FirebaseMessaging.getInstance().subscribeToTopic("Notification")
        }
    }

    //webview
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview1)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://insanmandiri.id/presensi")

        //webview setting
        val webSettings = webView.settings
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true

        // Request notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestNotificationPermission()
        } else {
            FirebaseMessaging.getInstance().subscribeToTopic("Notification")
        }
    }

    // backpress function
    @SuppressLint("MissingSuperCall")
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FirebaseMessaging.getInstance().subscribeToTopic("Notification")
            } else {
                // Handle permission denied case (optional)
            }
        }
    }
}