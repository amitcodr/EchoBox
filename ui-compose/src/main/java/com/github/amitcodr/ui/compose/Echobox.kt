package com.github.amitcodr.ui.compose

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.github.amitcodr.core.BugReport
import com.github.amitcodr.core.utils.AppInfoUtils

/**
 * EchoBox - a simple feedback/bug report dialog.
 *
 * Usage:
 * - If you want to receive reports via email:
 *      EchoBox().allowEmail("support@example.com").show(this)
 *
 * - If you want to send reports to your own backend (e.g., Firebase, REST API):
 *      EchoBox().onSubmit { report -> myApi.send(report) }.show(this)
 *
 * - Optionally, you can customize color and add dismiss callback.
 */
class EchoBox {

    private var email: String? = null
    private var color: Color = Color.Blue
    private var onDismissCallback: (() -> Unit)? = null
    private var onSubmitCallback: ((BugReport) -> Unit)? = null

    /**
     * Provide an email address if you want feedback to be sent via email.
     * Ignored if [onSubmit] is provided.
     */
    fun allowEmail(email: String): EchoBox {
        this.email = email
        return this
    }

    /**
     * Customize the primary color of the dialog.
     */
    fun setColor(color: Color): EchoBox {
        this.color = color
        return this
    }

    /**
     * Callback when the user dismisses the dialog (presses Cancel).
     */
    fun onDismiss(callback: () -> Unit): EchoBox {
        this.onDismissCallback = callback
        return this
    }

    /**
     * Callback when the user submits a report.
     * Use this to send feedback to your own backend, Firebase, or any API.
     * If this is set, [allowEmail] will be ignored.
     */
    fun onSubmit(callback: (BugReport) -> Unit): EchoBox {
        this.onSubmitCallback = callback
        return this
    }

    fun show(activity: ComponentActivity) {
        val metadata = AppInfoUtils.getAppMetadata(activity)

        activity.setContent {
            MaterialTheme(colorScheme = lightColorScheme(primary = color)) {
                var showDialog by remember { mutableStateOf(true) }

                if (showDialog) {
                    BugReportDialog(
                        onDismiss = {
                            showDialog = false
                            onDismissCallback?.invoke()
                        },
                        onSubmit = { report ->
                            showDialog = false
                            if (onSubmitCallback != null) {
                                onSubmitCallback?.invoke(report)
                            } else if (email != null) {
                                sendReportByEmail(activity, report, email!!)
                            }
                        },
                        metadata = metadata
                    )
                }
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
}