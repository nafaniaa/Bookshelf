import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.boolshelf.BooksApplication
import com.example.boolshelf.data.Book
import com.example.boolshelf.data.BooksRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

// Состояния UI для отображения различных состояний запроса
sealed interface BooksUiState {
    data class Success(val bookSearch: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

// ViewModel для управления данными и состоянием экрана
class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    // MutableState, предоставляющий информацию о текущем состоянии UI
    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    // Инициализация ViewModel, при создании сразу запрашиваются книги
    init {
        getBooks()
    }

    // Функция для получения книг с использованием корутин в viewModelScope
    fun getBooks(query: String = "book", maxResults: Int = 40) {
        viewModelScope.launch {
            booksUiState = BooksUiState.Loading // Устанавливаем состояние загрузки перед запросом

            // Попытка выполнить запрос к репозиторию
            booksUiState =
                try {
                    BooksUiState.Success(booksRepository.getBooks(query, maxResults))
                } catch (e: IOException) {
                    BooksUiState.Error
                } catch (e: HttpException) {
                    BooksUiState.Error
                }
        }
    }

    companion object {
        // Factory для создания ViewModel с использованием Hilt
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BooksApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}
