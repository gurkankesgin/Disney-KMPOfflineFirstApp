package ui.screens.characterlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.remote.model.Character
import disney.composeapp.generated.resources.Res
import disney.composeapp.generated.resources.disney
import disney.composeapp.generated.resources.header
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import ui.screens.characterlist.CharacterListViewModel.CharacterListUIState.Loading
import ui.screens.characterlist.CharacterListViewModel.CharacterListUIState.Success
import ui.screens.characterlist.item.CharacterListItem

/**
 * Created by gurkankesgin on 28.07.2024.
 */
@Composable
fun CharacterListScreen(viewModel: CharacterListViewModel = koinInject<CharacterListViewModel>()) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                title = {
                Text(
                    text = stringResource(Res.string.header)
                )
            }, navigationIcon = {
                Icon(
                    painter = painterResource(Res.drawable.disney),
                    contentDescription = null
                )
            })
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (val state = uiState.value) {
                is Loading -> Progress()
                is Success -> CharacterList(state.data)
            }
        }

    }
}

@Composable
private fun CharacterList(list: List<Character>?) {
    LazyColumn {
        items(list.orEmpty()) { item ->
            CharacterListItem(
                modifier = Modifier.padding(8.dp),
                imageUrl = item.imageUrl,
                name = item.name
            )
        }
    }
}

@Composable
private fun Progress() {
    CircularProgressIndicator()
}

