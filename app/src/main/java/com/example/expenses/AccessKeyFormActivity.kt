package com.example.expenses

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.expenses.ui.theme.AccessKeyFormTheme
import androidx.compose.material3.*
import com.example.expenses.ui.theme.Teal200

class AccessKeyFormActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccessKeyFormTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AccessKeyForm()
                }
            }
        }
    }

    @Composable
    private fun AccessKeyForm()
    {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val accessKey = remember { mutableStateOf(TextFieldValue()) }
                Text(
                    text = "Adicione a chave de acesso da nota fiscal",
                    textAlign = TextAlign.Center,
                    style = androidx.compose.material3.MaterialTheme.typography.displaySmall,
                    color = Color.Black,
                    //style = TextStyle(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    label = { Text(text = "Chave") },
                    value = accessKey.value,
                    onValueChange = { accessKey.value = it })
                Spacer(modifier = Modifier.height(20.dp))
                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = {
                             if (validateAccessKey(accessKey.value.text)) {
                                 val receiptURL = formatReceiptURL(accessKey.value.text)
                                 sendReceipt(receiptURL)
                                 finish()
                             }
                            else {
                                Toast.makeText(this@AccessKeyFormActivity, "Insira a sequencia de numeros presente na nota fiscal", Toast.LENGTH_SHORT).show()
                             }
                        },
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Teal200
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = "Enviar", color = Color.Black)
                    }
                }
            }
        }
    }
}