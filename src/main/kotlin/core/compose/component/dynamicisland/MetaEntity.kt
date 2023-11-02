package core.compose.component.dynamicisland

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun MetaEntity(
    modifier: Modifier = Modifier,
    blur: Dp = 12.dp,
    metaContent: @Composable BoxScope.() -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(modifier.width(IntrinsicSize.Min).height(IntrinsicSize.Min).clip(shape = RoundedCornerShape(22.dp)), contentAlignment = Alignment.Center) {
        Box(modifier = Modifier.blur(blur), content = metaContent)
        content()
    }
}