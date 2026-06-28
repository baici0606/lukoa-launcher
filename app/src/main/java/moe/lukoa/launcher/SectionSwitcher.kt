package moe.lukoa.launcher

import android.view.MotionEvent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

data class SectionSwitchOption<T>(
    val value: T,
    val label: String,
    val description: String,
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T> SectionSwitcherCard(
    title: String,
    options: List<SectionSwitchOption<T>>,
    selected: T,
    modifier: Modifier = Modifier,
    accentColor: Color = LukoaColors.Accent,
    onPagerLockChange: ((Boolean) -> Unit)? = null,
    onSelect: (T) -> Unit,
) {
    val selectedOption = options.firstOrNull { it.value == selected } ?: options.first()
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .pointerInteropFilter { event ->
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> onPagerLockChange?.invoke(true)
                    MotionEvent.ACTION_UP,
                    MotionEvent.ACTION_CANCEL -> onPagerLockChange?.invoke(false)
                }
                false
            },
        color = LukoaColors.Surface,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, LukoaColors.Line),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = title,
                color = LukoaColors.Text,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                options.forEach { option ->
                    SectionSwitchChip(
                        text = option.label,
                        selected = option.value == selected,
                        accentColor = accentColor,
                        onClick = { onSelect(option.value) },
                    )
                }
            }
            Text(
                text = selectedOption.description,
                color = LukoaColors.Muted,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun SectionSwitchChip(
    text: String,
    selected: Boolean,
    accentColor: Color,
    onClick: () -> Unit,
) {
    Surface(
        modifier = Modifier.clickable(onClick = rememberFeedbackClick(onClick)),
        color = if (selected) accentColor.copy(alpha = 0.14f) else LukoaColors.SurfaceAlt,
        shape = LukoaCapsuleShape,
        border = BorderStroke(
            1.dp,
            if (selected) accentColor.copy(alpha = 0.44f) else LukoaColors.Line,
        ),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
            color = if (selected) accentColor else LukoaColors.Muted,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
