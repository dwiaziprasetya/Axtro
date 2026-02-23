package com.example.axtro.presentation.welcome

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import com.example.axtro.data.model.sliderImage
import com.example.axtro.core.ui.theme.ListifyTheme
import com.example.axtro.core.ui.theme.poppinsFontFamily

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen() {

    val pagerState = rememberPagerState {
        sliderImage.size
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter)
        ) {
            HorizontalPager(
                state = pagerState
            ) { page ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Image(
                        painter = painterResource(sliderImage[page].imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .fillMaxWidth()
                            .height(300.dp),
                        contentScale = ContentScale.FillHeight
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(bottom = 50.dp)
                    ) {
                        Text(
                            lineHeight = 45.sp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            text = sliderImage[page].title,
                            fontFamily = poppinsFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp,
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            color = Color("#8d8d8d".toColorInt()),
                            text = sliderImage[page].description,
                            fontFamily = poppinsFontFamily,
                            fontSize = 16.sp,
                            maxLines = 3
                        )
                    }
                }
            }
            PageIndicator(
                numberOfPages = sliderImage.size,
                selectedPage = pagerState.currentPage,
                animationDurationMillis = 500
            )
            Spacer(modifier = Modifier.height(100.dp))
            Button(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .height(55.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    fontFamily = poppinsFontFamily,
                    text = "Get Started",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun PageIndicator(
    numberOfPages: Int,
    modifier: Modifier = Modifier,
    selectedPage: Int = 0,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    defaultColor: Color = Color.LightGray,
    defaultRadius: Dp = 15.dp,
    selectedRadius: Dp = 30.dp,
    space: Dp = 20.dp,
    animationDurationMillis: Int = 300
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space),
        modifier = modifier
    ) {
        for (i in 0 until numberOfPages) {
            val isSelected = i == selectedPage
            PagerIndicatorContent(
                isSelected = isSelected,
                selectedColor = selectedColor,
                defaultColor = defaultColor,
                defaultRadius = defaultRadius,
                selectedRadius = selectedRadius,
                animationDurationMillis = animationDurationMillis
            )
        }
    }
}

@Composable
fun PagerIndicatorContent(
    isSelected:Boolean,
    selectedColor: Color,
    defaultColor: Color,
    defaultRadius: Dp,
    selectedRadius: Dp,
    animationDurationMillis: Int,
    modifier: Modifier = Modifier
) {
    val color: Color by animateColorAsState(
        targetValue = if (isSelected) {
            selectedColor
        } else {
            defaultColor
        },
        animationSpec = tween(
            durationMillis = animationDurationMillis
        ), label = ""
    )

    val width: Dp by animateDpAsState(
        targetValue = if (isSelected) {
            selectedRadius
        } else {
            defaultRadius
        },
        animationSpec = tween(
            durationMillis = animationDurationMillis
        ), label = ""
    )

    Canvas(
        modifier = modifier
            .size(
                width = width,
                height = defaultRadius
            )
    ) {
        drawRoundRect(
            color = color,
            topLeft = Offset.Zero,
            size = Size(
                width = width.toPx(),
                height = defaultRadius.toPx(),
            ),
            cornerRadius = CornerRadius(
                x = defaultRadius.toPx(),
                y = defaultRadius.toPx()
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PageIndicatorPreview() {
    ListifyTheme(dynamicColor = false) {
        PageIndicator(
            numberOfPages = 3,
            selectedPage = 0
        )
    }
}

@Preview
@Composable
private fun WelcomeScreenPreview() {
    ListifyTheme(dynamicColor = false) {
        WelcomeScreen()
    }
}