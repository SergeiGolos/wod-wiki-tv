package com.example.gymtvapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.tv.material3.*
import com.example.gymtvapp.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import android.graphics.Bitmap
import android.graphics.Color as AndroidColor // Alias to avoid clash with Compose Color

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun TimerScreen(
    timerValue: String = "00:00:00",
    qrCodeBitmap: Bitmap? = null,
    connectedUsers: List<UserData> = emptyList()
) {
    var isQrCodeFocused by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        shape = RectangleShape // No rounded corners for the main surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp) // Overscan safety margin
        ) {
            // Left Column: Timer and Connected Users
            Column(
                modifier = Modifier
                    .weight(0.6f) // Takes 60% of the width
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround // Distribute space
            ) {
                // Timer Display
                Text(
                    text = timerValue,
                    style = MaterialTheme.typography.displayLarge.copy(fontSize = 100.sp), // Large text for timer
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // Connected Users Display Area (Placeholder)
                if (connectedUsers.isNotEmpty()) {
                    ConnectedUsersList(users = connectedUsers)
                } else {
                    Text(
                        text = "No users connected.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Right Column: QR Code
            Column(
                modifier = Modifier
                    .weight(0.4f) // Takes 40% of the width
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (qrCodeBitmap != null) {
                    Image(
                        bitmap = qrCodeBitmap.asImageBitmap(),
                        contentDescription = LocalContext.current.getString(R.string.qr_code_description),
                        modifier = Modifier
                            .size(280.dp) // Larger QR code for TV
                            .onFocusChanged { focusState ->
                                isQrCodeFocused = focusState.isFocused
                            }
                            .focusable() // Make it focusable
                            .padding(8.dp) // Padding around the QR code
                            .border( // Visual feedback for focus
                                width = if (isQrCodeFocused) 4.dp else 2.dp,
                                color = if (isQrCodeFocused) MaterialTheme.colorScheme.primary else Color.Gray,
                                shape = MaterialTheme.shapes.medium
                            )
                            .padding(8.dp), // Inner padding for the image itself
                        contentScale = ContentScale.Fit
                    )
                } else {
                    // Placeholder if QR code is not yet generated
                    Box(
                        modifier = Modifier
                            .size(280.dp)
                            .background(Color.Gray.copy(alpha = 0.5f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Generating QR Code...",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = LocalContext.current.getString(R.string.qr_code_description),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun ConnectedUsersList(users: List<UserData>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Connected Users:",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        users.forEach { user ->
            UserRow(userData = user)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun UserRow(userData: UserData) {
    var isFocused by remember { mutableStateOf(false) }
    Card(
        onClick = { /* Optional: Action when user row is clicked */ },
        modifier = Modifier
            .fillMaxWidth(0.8f) // Take 80% of parent column width
            .onFocusChanged { focusState ->
                isFocused = focusState.isFocused
            }
            .focusable(), // Make card focusable
        shape = CardDefaults.shape(MaterialTheme.shapes.medium),
        colors = CardDefaults.colors(
            containerColor = if (isFocused) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        scale = CardDefaults.scale(focusedScale = 1.05f) // Slight zoom on focus
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "User: ${userData.id.take(8)}...", // Show partial ID
                style = MaterialTheme.typography.bodyLarge
            )
            Row {
                Text(
                    text = "${LocalContext.current.getString(R.string.heart_rate_label)} ${userData.heartRate?.toString() ?: LocalContext.current.getString(R.string.not_available_short)} ${LocalContext.current.getString(R.string.bpm_unit)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Text(
                    text = "${LocalContext.current.getString(R.string.gps_label)} ${if (userData.gpsLat != null && userData.gpsLon != null) "Yes" else LocalContext.current.getString(R.string.not_available_short)}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Dummy data for preview
data class UserData(
    val id: String,
    val heartRate: Int? = null,
    val gpsLat: Double? = null,
    val gpsLon: Double? = null
)

// Function to generate a placeholder QR code for preview
fun generatePreviewQrCode(size: Int = 256): Bitmap {
    val writer = QRCodeWriter()
    val bitMatrix = writer.encode(
        "preview_qr_code_content",
        BarcodeFormat.QR_CODE,
        size,
        size,
        mapOf(EncodeHintType.MARGIN to 1)
    )
    val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
    for (x in 0 until size) {
        for (y in 0 until size) {
            bmp.setPixel(x, y, if (bitMatrix[x, y]) AndroidColor.BLACK else AndroidColor.WHITE)
        }
    }
    return bmp
}


@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p", showBackground = true, backgroundColor = 0xFF212121)
@Composable
fun TimerScreenPreview() {
    MaterialTheme { // Ensure TV Material Theme is applied for preview
        TimerScreen(
            timerValue = "00:15:30",
            qrCodeBitmap = generatePreviewQrCode(),
            connectedUsers = listOf(
                UserData("UserA123XYZ", 120, 34.05, -118.25),
                UserData("UserB456ABC", 98, null, null),
                UserData("UserC789DEF", 150, 34.07, -118.22)
            )
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p", showBackground = true, backgroundColor = 0xFF212121)
@Composable
fun TimerScreenEmptyPreview() {
    MaterialTheme {
        TimerScreen(
            timerValue = "00:00:00",
            qrCodeBitmap = generatePreviewQrCode(128) // smaller for this preview
        )
    }
}

@OptIn(ExperimentalTvMaterial3Api::class)
@Preview(device = "id:tv_1080p", showBackground = true, backgroundColor = 0xFF212121)
@Composable
fun TimerScreenNoQrPreview() {
    MaterialTheme {
        TimerScreen(
            timerValue = "01:00:12",
            qrCodeBitmap = null,
            connectedUsers = listOf(
                UserData("UserOnly1", 110)
            )
        )
    }
}
