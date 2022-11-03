package com.book.manager.presentation.handler

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class BookManagerAuthenticationEntryPoint : AuthenticationEntryPoint {
    // 未認証の状態のユーザで認証が必要な API にアクセスしたときにの処理を記述
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
    }
}
