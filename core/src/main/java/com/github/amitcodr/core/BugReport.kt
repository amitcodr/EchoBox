package com.github.amitcodr.core

data class BugReport(
    val sentiment: Sentiment,
    val problem: String,
    val suggestion: String? = null,
    val metadata: Map<String, String> = emptyMap()
)

enum class Sentiment(val emoji: String) {
    HAPPY("😊"),
    CONFUSED("😕"),
    SAD("😢"),
    ANGRY("😡")
}