package com.github.amitcodr.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.amitcodr.core.BugReport
import com.github.amitcodr.core.Sentiment

@Composable
fun BugReportDialog(
    onDismiss: () -> Unit,
    onSubmit: (BugReport) -> Unit,
    metadata: Map<String, String>,
    modifier: Modifier = Modifier
) {
    var selectedSentiment by remember { mutableStateOf<Sentiment?>(null) }
    var problem by remember { mutableStateOf("") }
    var suggestion by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Report Feedback") },
        text = {
            Column(modifier = modifier.verticalScroll(rememberScrollState())) {
                Text("How do you feel?")
                Spacer(Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Sentiment.entries.forEach { sentiment ->
                        val isSelected = sentiment == selectedSentiment
                        Text(
                            text = sentiment.emoji,
                            fontSize = 32.sp,
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                    else Color.Transparent
                                )
                                .clickable { selectedSentiment = sentiment }
                                .padding(8.dp)
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = problem,
                    onValueChange = { problem = it },
                    label = { Text("What went wrong?") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = suggestion,
                    onValueChange = { suggestion = it },
                    label = { Text("Any suggestions? (optional)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                enabled = selectedSentiment != null && problem.isNotBlank(),
                onClick = {
                    selectedSentiment?.let {
                        onSubmit(
                            BugReport(
                                sentiment = it,
                                problem = problem,
                                suggestion = suggestion.takeIf { s -> s.isNotBlank() },
                                metadata = metadata
                            )
                        )
                    }
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}