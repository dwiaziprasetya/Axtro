package com.example.listify.data.model

import com.example.listify.R

data class ImageSliderItem(
    val imageUrl: Int,
    val title: String,
    val description: String
)

val sliderImage = listOf(
    ImageSliderItem(
        imageUrl = R.drawable.image_welcome_2,
        title = "Manage your task",
        description = "Optimize your workflow and get more done and " +
                "achieve your goals with better task management"
    ),
    ImageSliderItem(
        imageUrl = R.drawable.image_welcome,
        title = "Stay Organized",
        description = "Effortlessly manage and complete your to-dos and " +
                "Never miss a deadline with our efficient task manager"
    ),
    ImageSliderItem(
        imageUrl = R.drawable.image_welcome_3,
        title = "Maintain Control",
        description = "Easily keep track of your daily tasks and " +
                "optimize your entire workflow to achieve the highest level of productivity"
    ),
)
