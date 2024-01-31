package com.example.boolshelf.network

import com.example.bookshefl.BookShefl
import retrofit2.http.GET
import retrofit2.http.Query

interface BookService {
    @GET("volumes")
    suspend fun bookSearch(
        @Query("q") searchQuery: String, //хранит текст поискового запроса
        @Query("maxResults") maxResults: Int //размер списка книг в ответе сервера
    ): BookShefl
}