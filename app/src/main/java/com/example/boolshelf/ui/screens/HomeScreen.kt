package com.example.boolshelf.ui.screens

import BooksUiState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    booksUiState: BooksUiState,
    retryAction: () -> Unit,
    modifier: Modifier
) {
    when (booksUiState) {
        is BooksUiState.Loading -> LoadingScreen(modifier)
        is BooksUiState.Success -> BooksGridScreen(
            books = booksUiState.bookSearch,
            modifier = modifier
        )
        is BooksUiState.Error -> ErrorScreen(retryAction = retryAction, modifier)
        else -> null
    }
}