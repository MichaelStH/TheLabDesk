package com.riders.thelabdesk.core.ui.compose.component.dynamicisland

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.riders.thelabdesk.core.ui.base.UiEvent
import com.riders.thelabdesk.core.ui.compose.component.TheLabDeskSurface
import com.riders.thelabdesk.core.ui.compose.theme.TheLabDeskTheme
import com.riders.thelabdesk.core.ui.data.local.compose.IslandUiState
import org.intellij.lang.annotations.Language

@Language("AGSL")
const val ShaderSource = """
    uniform shader composable;
    
    uniform float cutoff;
    
    half4 main(float2 fragCoord) {
        half4 color = composable.eval(fragCoord);
        float alpha = color.a;
        if (alpha > cutoff) {
            alpha = 1.0;
        } else {
            alpha = 0.0;
        }
        
        color = half4(color.r, color.g, color.b, alpha);
        return color;
    }
"""

@Composable
fun MetaContainer(
    modifier: Modifier,
    cutoff: Float = .5f,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .background(Color.Transparent)
            .clip(shape = RoundedCornerShape(22.dp)),
        content = content
    )
}

@Composable
fun MetaContainer(
    state: IslandUiState,
    searchRequest: String,
    isSearchFocused: Boolean,
    uiEvent: (UiEvent) -> Unit
) {
    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            Box(
                modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                MetaContainer(modifier = Modifier) {
                    IslandContent(
                        state = IslandUiState.SearchState(),
                        searchRequest = searchRequest,
                        isSearchFocused = isSearchFocused,
                        uiEvent = uiEvent
                    )
                }
            }
        }
    }
}