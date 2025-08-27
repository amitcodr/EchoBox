package com.github.amitcodr.echobox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.amitcodr.ui.compose.EchoBox

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var showEchoBox by remember { mutableStateOf(false) }

            MaterialTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { showEchoBox = true }) {
                        Text("Open EchoBox")
                    }

                    if (showEchoBox) {
                        EchoBox()
                            .allowEmail("support@example.com")
                            .setColor(Color.Blue)
                            .onDismiss {
                                showEchoBox = false
                            }
                            .show(activity = this@MainActivity)
                    }
                }
            }
        }
    }
}