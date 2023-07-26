package com.example.expenses

import android.os.Bundle
import android.view.Surface
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expenses.PreviewViewComposable
import com.google.android.gms.vision.text.Text

class QRCodeScannerActivity : AppCompatActivity() {
    @androidx.camera.core.ExperimentalGetImage
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRScanner3Theme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PreviewViewComposable()
                        Text(
                            text = "Scan QR Code",
                            modifier = Modifier.padding(top = 48.dp)
                        )

                    }
                }
            }
        }
    }
}