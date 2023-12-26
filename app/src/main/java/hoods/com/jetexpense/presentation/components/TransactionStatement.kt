package hoods.com.jetexpense.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.DismissValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> TransactionStatement(
    modifier: Modifier = Modifier,
    items: List<T>,
    colors: (T) -> Color,
    amounts: (T) -> Float,
    amountsTotal: Float,
    circleLabel: String,
    onItemSwiped: (T) -> Unit,
    key: (T) -> Int,
    rows: @Composable (T) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        item {
            Box(Modifier.padding(16.dp)) {
                val transProportions = items.extractProportion { amounts(it) }
                val circleColor = items.map { colors(it) }
                AnimatedCircle(
                    proportions = transProportions,
                    colors = circleColor,
                    modifier = Modifier
                        .height(300.dp)
                        .align(Alignment.Center)
                        .fillMaxWidth()
                )
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = circleLabel,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = formatAmount(amountsTotal),
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))

        }

        items(items, key = key) { item ->
            val dismissState = rememberDismissState(
                initialValue = DismissValue.Default
            )
            val isItemDismissed =
                dismissState.isDismissed(DismissDirection.StartToEnd)
            val isDismissedToEnd =
                dismissState.targetValue == DismissValue.DismissedToEnd
            SwipeToDismiss(
                state = dismissState,
                background = {
                    AnimatedVisibility(isDismissedToEnd) {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                                .height(68.dp),
                            color = MaterialTheme.colorScheme.error,
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                },
                dismissContent = {
                    if (isItemDismissed) {
                        onItemSwiped.invoke(item)
                    }
                    rows(item)
                },
                directions = setOf(DismissDirection.StartToEnd)
            )

        }
    }
}


@Composable
fun AnimatedCircle(
    proportions: List<Float>,
    colors: List<Color>,
    modifier: Modifier = Modifier,
) {
    val currentState = remember {
        MutableTransitionState(AnimatedCircleProgress.START)
            .apply {
                targetState = AnimatedCircleProgress.END
            }
    }
    val stroke = with(LocalDensity.current) { Stroke(5.dp.toPx()) }
    val transition = updateTransition(transitionState = currentState, label = "Circle")
    val angleOffset by transition.animateFloat(
        transitionSpec = {
            tween(
                durationMillis = 900,
                delayMillis = 500,
                easing = LinearOutSlowInEasing
            )
        },
        label = ""
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }

    val shift by transition.animateFloat(
        transitionSpec = {
            tween(
                delayMillis = 500,
                durationMillis = 900,
                easing = CubicBezierEasing(0f, 0.75f, 0.35f, 0.85f)
            )
        }, label = ""
    ) { progress ->
        if (progress == AnimatedCircleProgress.START) {
            0f
        } else {
            360f
        }
    }

    Canvas(modifier = modifier) {
        val innerRadius = (size.minDimension - stroke.width) / 2
        val halfSize = size / 2.0f
        val topLeft = Offset(
            x = halfSize.width - innerRadius,
            y = halfSize.height - innerRadius
        )
        val size = Size(innerRadius * 2, innerRadius * 2)
        var startAngle = shift - 90f
        proportions.forEachIndexed { index, proportions ->
            val sweep = proportions * angleOffset
            drawArc(
                color = colors[index],
                startAngle = startAngle + DividerLengthInDegrees / 2,
                sweepAngle = sweep - DividerLengthInDegrees,
                topLeft = topLeft,
                size = size,
                useCenter = false,
                style = stroke
            )
            startAngle += sweep
        }
    }

}

private enum class AnimatedCircleProgress { START, END }

private const val DividerLengthInDegrees = 1.8f


fun <E> List<E>.extractProportion(selector: (E) -> Float): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}


























