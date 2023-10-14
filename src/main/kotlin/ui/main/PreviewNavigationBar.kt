package ui.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.compose.theme.TheLabDeskTheme
import data.local.bean.NavigationItemType
import di.AppModule
import viewmodel.MainViewModel


//////////////////////////////////////////////////
//
// COMPOSE
//
//////////////////////////////////////////////////
@Composable
fun NavigationBar(viewModel: MainViewModel) {
    val state = rememberLazyListState()

    TheLabDeskTheme(viewModel.isDarkMode) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
            // Optional, for accessibility purpose,
            // .selectableGroup()
            ,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            state = state
        ) {
            item {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth().heightIn(0.dp, 300.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    itemsIndexed(items = viewModel.navigationOptions) { index, item ->

                        // Create a composable function which will take list of NavigationUiState
                        // and a callback to call when any selection is tapped by user on UI
                        if (item.navigationItemType == NavigationItemType.DEFAULT) {
                            NavigationBarItem(item, viewModel::updateNavigationItemSelected)
                        }
                    }
                }
            }


            item {
                if (null != viewModel.navigationOptions.find { it.navigationItemType == NavigationItemType.SETTINGS }) {
                    val settingsItem =
                        viewModel.navigationOptions.find { it.navigationItemType == NavigationItemType.SETTINGS }!!
                    NavigationBarItem(settingsItem, viewModel::updateNavigationItemSelected)
                }
            }
        }
    }
}

//////////////////////////////////////////////////
//
// PREVIEWS
//
//////////////////////////////////////////////////
@Preview
@Composable
private fun PreviewNavigationBar() {
    val viewModel: MainViewModel = MainViewModel(AppModule.injectDependencies())
    viewModel.updateDarkMode(true)
    TheLabDeskTheme(true) {
        NavigationBar(viewModel = viewModel)
    }
}
