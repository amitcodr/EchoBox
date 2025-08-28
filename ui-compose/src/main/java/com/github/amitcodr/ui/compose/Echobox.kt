package com.github.amitcodr.ui.compose

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.github.amitcodr.core.BugReport
import com.github.amitcodr.core.utils.AppInfoUtils

@Composable
fun EchoBox(
    activity: ComponentActivity,
    email: String? = null,
    color: Color = Color.Blue,
    onDismiss: (() -> Unit)? = null,
    onSubmit: ((BugReport) -> Unit)? = null,
    onClose: (() -> Unit)? = null
) {
    val metadata = AppInfoUtils.getAppMetadata(activity)

    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        MaterialTheme(colorScheme = lightColorScheme(primary = color)) {
            BugReportDialog(
                onDismiss = {
                    showDialog = false
                    onDismiss?.invoke()
                    onClose?.invoke()
                },
                onSubmit = { report ->
                    showDialog = false
                    onClose?.invoke()

                    if (onSubmit != null) {
                        onSubmit(report)
                    } else if (email != null) {
                        sendReportByEmail(activity, report, email)
                    }
                },
                metadata = metadata
            )
        }
    }
}

private fun sendReportByEmail(
    context: Context,
    report: BugReport,
    email: String
) {
    val subject = "User Feedback (${report.sentiment})"
    val body = buildString {
        appendLine("Sentiment: ${report.sentiment}")
        appendLine("Problem: ${report.problem}")
        appendLine("Suggestion: ${report.suggestion ?: "None"}")
        appendLine()
        appendLine("Metadata:")
        report.metadata.forEach { (k, v) -> appendLine(" - $k: $v") }
    }

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "message/rfc822"
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, body)
    }

    try {
        context.startActivity(Intent.createChooser(intent, "Send Email"))
    } catch (_: ActivityNotFoundException) {
        Toast.makeText(context, "No email app found.", Toast.LENGTH_SHORT).show()
    }
}