package com.example.biometrictest

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import androidx.biometric.BiometricPrompt
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val newExecutor: Executor = Executors.newSingleThreadExecutor()

        val myBiometricPrompt =
            BiometricPrompt(
                this,
                newExecutor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, @NonNull errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                        } else {
                            Toast.makeText(
                                this@MainActivity,
                                "Error code $errorCode: $errString",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onAuthenticationSucceeded(@NonNull result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        Log.d(DEBUG_TAG, "Fingerprint recognized successfully")
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Log.d(DEBUG_TAG, "Fingerprint not recognized")
                        Toast.makeText(
                            this@MainActivity,
                            "Fingerprint not recognized",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Here's a fun title")
            .setSubtitle("This is a great subtitle")
            .setDescription("Wow, what a cool description")
            .setNegativeButtonText("Cancel")
            .build()

        launchAuthentication.setOnClickListener {
            myBiometricPrompt.authenticate(promptInfo)
        }
    }

    companion object {
        private const val DEBUG_TAG = "MainActivity"
    }
}
