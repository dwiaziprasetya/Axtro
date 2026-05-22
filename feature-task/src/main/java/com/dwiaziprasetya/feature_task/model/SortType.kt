package com.dwiaziprasetya.feature_task.model

enum class SortType(
    val title: String
) {
    A_TO_Z("A to Z"),
    Z_TO_A("Z to A"),
    PRIORITY_LOW_TO_HIGH("Priority: Low to High"),
    PRIORITY_HIGH_TO_LOW("Priority: High to Low"),
    DATE_ASCENDING("Date Ascending"),
    DATE_DESCENDING("Date Descending")
}