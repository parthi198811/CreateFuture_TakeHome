package com.createfuture.takehome.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.createfuture.takehome.R
import com.createfuture.takehome.domain.model.Character
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ComposeHomeFragment : Fragment() {

    val viewModel by viewModels<CharactersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = ComposeView(requireContext()).apply {

        setContent {
            val uiState by viewModel.uiState.collectAsState()
            var searchQuery by remember { mutableStateOf("") }

            Scaffold { contentPadding ->

                Column(modifier = Modifier.padding(contentPadding)) {
                    when (uiState) {
                        is DeviceListState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is DeviceListState.Success -> {
                            val character = (uiState as DeviceListState.Success).characters
                            CharacterList(
                                searchQuery = searchQuery,
                                character = character
                            ) {
                                searchQuery = it
                                viewModel.searchDevices(it)
                            }
                        }

                        is DeviceListState.Error -> {
                            Text(
                                text = "Error: ${(uiState as DeviceListState.Error).message}",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterList(
    modifier: Modifier = Modifier,
    character: List<Character>,
    searchQuery: String,
    onQueryChange: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.img_characters),
                contentScale = ContentScale.FillBounds
            )
    ) {

        BasicTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            textStyle = TextStyle(color = Color.White),
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF333333)),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text = "Search...",
                            color = Color.White
                        )
                    }
                    innerTextField()
                }
            }
        )

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            for (character in character) {
                Row {
                    Column {
                        Text(text = character.name, color = Color.White, fontSize = 18.sp)
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Text(text = "Culture: ", color = Color.White, fontSize = 16.sp)
                            Text(text = character.culture, color = Color.Gray, fontSize = 16.sp)
                        }
                        Row {
                            Text("Born: ", color = Color.White, fontSize = 16.sp)
                            Text(text = character.born, color = Color.Gray, fontSize = 16.sp)
                        }
                        Row {
                            Text(text = "Died: ", color = Color.White, fontSize = 16.sp)
                            Text(text = character.died, color = Color.Gray, fontSize = 16.sp)
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column {
                        Text("Seasons: ", color = Color.White, fontSize = 14.sp)
                        var seasons = character.tvSeries.joinToString(",") {
                            when (it) {
                                "Season 1" -> "I "
                                "Season 2" -> "II"
                                "Season 3" -> "III"
                                "Season 4" -> "IV"
                                "Season 5" -> "V"
                                "Season 6" -> "VI"
                                "Season 7" -> "VII"
                                "Season 8" -> "VIII"
                                else -> ""
                            }
                        }
                        Text(seasons, color = Color.Gray, fontSize = 14.sp)
                    }
                }
                HorizontalDivider(
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp),
                    thickness = 1.dp, color = Color.Gray
                )
            }
        }
    }
}