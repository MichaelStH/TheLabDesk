package core.compose.component.dynamicisland

import androidx.compose.desktop.ui.tooling.preview.Preview
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
import core.compose.component.TheLabDeskSurface
import core.compose.theme.TheLabDeskTheme
import data.local.model.compose.IslandUiState
import di.AppModule
import org.intellij.lang.annotations.Language
import ui.main.MainViewModel

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

@Preview
@Composable
fun MetaContainer() {
    val viewModel = MainViewModel(AppModule.injectDependencies())

    TheLabDeskTheme {
        TheLabDeskSurface(modifier = Modifier) {
            Box(
                modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                MetaContainer(modifier = Modifier) {
                    IslandContent(viewModel = viewModel, state = IslandUiState.SearchState())
                }
            }
        }
    }
}