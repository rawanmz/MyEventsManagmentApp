package com.example.myeventsmanagmentapp.screens.task

import android.graphics.Typeface
import android.text.Layout
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myeventsmanagmentapp.formatDateToDay
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberAxisGuidelineComponent
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.component.rememberLayeredComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shape.markerCornered
import com.patrykandpatrick.vico.core.cartesian.CartesianMeasureContext
import com.patrykandpatrick.vico.core.cartesian.HorizontalDimensions
import com.patrykandpatrick.vico.core.cartesian.Insets
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.marker.CartesianMarker
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Corner
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import com.example.myeventsmanagmentapp.ui.theme.LightBlue
import com.example.myeventsmanagmentapp.ui.theme.LightGreen
import com.example.myeventsmanagmentapp.ui.theme.LightPurple
import com.example.myeventsmanagmentapp.ui.theme.LightRed
import com.example.myeventsmanagmentapp.ui.theme.PrimaryColor
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer


@Composable
internal fun rememberMarker(
    labelPosition: DefaultCartesianMarker.LabelPosition = DefaultCartesianMarker.LabelPosition.Top,
    showIndicator: Boolean = true,
): CartesianMarker {
    val labelBackgroundShape = Shape.markerCornered(Corner.FullyRounded)
    val labelBackground =
        rememberShapeComponent(labelBackgroundShape, MaterialTheme.colorScheme.surface)
            .setShadow(
                radius = LABEL_BACKGROUND_SHADOW_RADIUS_DP,
                dy = LABEL_BACKGROUND_SHADOW_DY_DP,
                applyElevationOverlay = true,
            )
    val label =
        rememberTextComponent(
            color = MaterialTheme.colorScheme.onSurface,
            background = labelBackground,
            padding = Dimensions.of(8.dp, 4.dp),
            typeface = Typeface.MONOSPACE,
            textAlignment = Layout.Alignment.ALIGN_CENTER,
            minWidth = TextComponent.MinWidth.fixed(40f),
        )
    val indicatorFrontComponent =
        rememberShapeComponent(Shape.Pill, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = rememberShapeComponent(Shape.Pill)
    val indicatorRearComponent = rememberShapeComponent(Shape.Pill)
    val indicator =
        rememberLayeredComponent(
            rear = indicatorRearComponent,
            front =
            rememberLayeredComponent(
                rear = indicatorCenterComponent,
                front = indicatorFrontComponent,
                padding = Dimensions.of(5.dp),
            ),
            padding = Dimensions.of(10.dp),
        )
    val guideline = rememberAxisGuidelineComponent()
    return remember(label, labelPosition, indicator, showIndicator, guideline) {
        object : DefaultCartesianMarker(
            label = label,
            labelPosition = labelPosition,
            indicator = if (showIndicator) indicator else null,
            indicatorSizeDp = 36f,
            setIndicatorColor =
            if (showIndicator) {
                { color ->
                    indicatorRearComponent.color = color
                    indicatorCenterComponent.color = color
                    indicatorCenterComponent.setShadow(radius = 12f, color = color)
                }
            } else {
                null
            },
            guideline = guideline,
        ) {
            override fun getInsets(
                context: CartesianMeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) {
                with(context) {
                    outInsets.top =
                        (CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER * LABEL_BACKGROUND_SHADOW_RADIUS_DP -
                                LABEL_BACKGROUND_SHADOW_DY_DP).pixels
                    if (labelPosition == LabelPosition.AroundPoint) return
                    outInsets.top += label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels
                }
            }
        }
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS_DP = 4f
private const val LABEL_BACKGROUND_SHADOW_DY_DP = 2f
private const val CLIPPING_FREE_SHADOW_RADIUS_MULTIPLIER = 1.4f


@Composable
internal fun CurrentWeekTask(
    modifier: Modifier,
    viewModel: TaskViewModel,
) {
    val data = viewModel.taskInWeek.collectAsState(initial = null)

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(data) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.tryRunTransaction {
                    val groupedByDate = data.value?.groupBy { it.task.date }
                    if (groupedByDate?.values?.isNotEmpty() == true) {
                        columnSeries {
                            repeat(1) {//groupedByDate?.size?:0) {
                                series(
                                    groupedByDate.values.map { it.size }
                                )
                            }
                        }
                    }
                }
            }
        }
    }


    val groupedByDate = data.value?.groupBy { formatDateToDay(it.task.date) }
    groupedByDate?.keys?.let { CurrentWeekTaskChart(modelProducer, modifier, it) }

}

@Composable
fun CurrentWeekTaskChart(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
    keys: Set<String>,
) {
    val horizontalBox = rememberHorizontalBox()
    val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->

        keys.toList()[x.toInt() % keys.size]
    }
    val shape = remember { Shape.cut(topLeftPercent = 50) }
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
                    columnColors.map {
                        rememberLineComponent(
                            color = it,
                            thickness = 8.dp,
                            shape = shape
                        )
                    },
                ),
            ),
            startAxis = rememberStartAxis(),
            bottomAxis = rememberBottomAxis(valueFormatter = bottomAxisValueFormatter),
            decorations = remember(horizontalBox) { listOf(horizontalBox) },
        ),
        modelProducer = modelProducer,
        modifier = modifier,
        marker = rememberMarker(),
        runInitialAnimation = false,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}

@Composable
fun Chart5(
    modifier: Modifier,
    viewmodel: TaskViewModel,
) {
    val data = viewmodel.tagWithTasks.collectAsState(initial = null)

    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.tryRunTransaction {


                    if (data.value?.isNotEmpty() == true) {
                        columnSeries {
                            val list = data.value?.map { it.tasks.size }
                            repeat(3) {
                                val tasksCount = data.value?.map { it.tasks.size }
                                if (tasksCount != null) {
                                    series(
                                        tasksCount
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
    val groupedByDate = data.value?.groupBy { it.tag.name }
    groupedByDate?.keys?.let { ComposeChart5(modelProducer, modifier, it) }

}

@Composable
private fun ComposeChart5(
    modelProducer: CartesianChartModelProducer,
    modifier: Modifier,
    strings: Set<String>,
) {
    val bottomAxisValueFormatter = CartesianValueFormatter { x, _, _ ->

        strings.toList()[x.toInt() % strings.size]
    }
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider =
                ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(
                        color = LightGreen,
                        thickness = COLUMN_THICKNESS_DP.dp,
                        shape =
                        Shape.rounded(
                            bottomLeftPercent = COLUMN_ROUNDNESS_PERCENT,
                            bottomRightPercent = COLUMN_ROUNDNESS_PERCENT,
                        ),
                    ),
                    rememberLineComponent(
                        color = LightRed,
                        thickness = COLUMN_THICKNESS_DP.dp,
                    ),
                    rememberLineComponent(
                        color = LightPurple,
                        thickness = COLUMN_THICKNESS_DP.dp,
                        shape =
                        Shape.rounded(
                            topLeftPercent = COLUMN_ROUNDNESS_PERCENT,
                            topRightPercent = COLUMN_ROUNDNESS_PERCENT,
                        ),
                    ),
                ),
                mergeMode = { ColumnCartesianLayer.MergeMode.Stacked },
            ),
            startAxis =
            rememberStartAxis(
                itemPlacer = startAxisItemPlacer,
                labelRotationDegrees = AXIS_LABEL_ROTATION_DEGREES,
            ),
            bottomAxis = rememberBottomAxis(valueFormatter = bottomAxisValueFormatter),

            ),
        modelProducer = modelProducer,
        modifier = modifier,
        marker = rememberMarker(),
        runInitialAnimation = false,
        zoomState = rememberVicoZoomState(zoomEnabled = false),
    )
}


private const val COLUMN_ROUNDNESS_PERCENT: Int = 40
private const val COLUMN_THICKNESS_DP: Int = 10
private const val AXIS_LABEL_ROTATION_DEGREES = 45f


private val startAxisItemPlacer = AxisItemPlacer.Vertical.count({ 3 })

@Composable
private fun rememberHorizontalBox() =
    com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalBox(
        y = { 7f..14f },
        box = rememberShapeComponent(color = horizontalBoxColor.copy(.36f)),
        labelComponent =
        rememberTextComponent(
            color = Color.Black,
            background = rememberShapeComponent(
                Shape.Rectangle,
                horizontalBoxColor
            ),
            padding = Dimensions.of(8.dp, 2.dp),
            margins = Dimensions.of(4.dp),
            typeface = Typeface.MONOSPACE,
        ),
    )

private val columnColors = listOf(PrimaryColor, LightGreen, LightBlue)
private val horizontalBoxColor = Color(0xffe9e5af)
