package com.book.manager.presentation.controller

import com.book.manager.application.service.BookService
import com.book.manager.presentation.form.BookInfo
import com.book.manager.presentation.form.GetBookListResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

// API のルーティングや、クライアントからのパラメータをつけ取って Service のロジックを実行する層
@RestController
@RequestMapping("book")
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/list")
    fun getList(): GetBookListResponse {
        val bookList = bookService.getList().map {
            BookInfo(it)
        }
        // 最終的に、モデルクラスをレスポンスクラス型に変換して返す
        return GetBookListResponse(bookList)
    }
}
