package com.example.expenses

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expenses.ui.theme.QRCodeScannerTheme
import com.example.expenses.ui.theme.Teal200
import com.example.expenses.ui.theme.primary
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QRCodeScannerActivity : ComponentActivity() {
    @Inject
    lateinit var qrCodeScanner: QRCodeScanner

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRCodeScannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = primary
                ) {
                    val barcodeResults =
                        qrCodeScanner.barCodeResults.collectAsStateWithLifecycle()
//                    val apiResult = qrCodeScanner.receiptAPIResult
//                    ScanBarcode(
//                        qrCodeScanner::startScan,
////                        qrCodeScanner.receiptAPIResult
//                    )
                    StartScanBarcode(onScanBarcode = qrCodeScanner::startScan)
//                    finish()
                    Log.d("qrcode-scanner", "finished scanning")

                    val intent = Intent(this, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    this.startActivity(intent)
                }
            }
        }
    }
}

@Composable
private fun StartScanBarcode (
    onScanBarcode: suspend () -> Unit
) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(scope) {
        onScanBarcode()
    }

}
@Composable
private fun ScanBarcode(
    onScanBarcode: suspend () -> Unit
//    receiptAPIResult: String?
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth(.85f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Teal200
            ),
            onClick = {
                scope.launch {
                    onScanBarcode()
                }
            }) {
            Text(
                text = "Adicionar nota fiscal via QRCode",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black,
                //style = TextStyle(fontWeight = FontWeight.Bold)
            )
//            showResultMessage(context, "Nota fiscal enviada. Veja o status da extracao na tela inicial")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(.85f),
            colors = ButtonDefaults.buttonColors(
                containerColor = Teal200
            ),
            onClick = {

            }) {
            Text(
                text = "Adicionar nota fiscal digitando Chave de Acesso",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                color = Color.Black,
                //style = TextStyle(fontWeight = FontWeight.Bold)
            )
        }

//        Text(
//            text = receiptAPIResult ?: "",
//            style = MaterialTheme.typography.displaySmall,
//        )
    }
}

fun showResultMessage(context: Context, message:String){
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Preview
@Composable
fun PreviewScanBarcode() {
    QRCodeScannerTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = primary
        ) {

            ScanBarcode({})
        }
    }
}

//fun AccessKeyForm() {
//    var textState by remember { mutableStateOf("Hello") }
//    Column(modifier = Modifier.padding(16.dp)) {
//        TextField(
//            value = textState,
//            singleLine = true,
//            modifier = Modifier.fillMaxWidth(),
//            onValueChange = { textState=it },
//            label = { Text(text = "Entre a chave de acesso") }
//        )
////        mainUiState.currentNameErrors.forEach {
////            Text(
////                modifier = Modifier.padding(vertical = 8.dp),
////                text = it,
////                color = Color.Red
////            )
////        }
//    }
//}