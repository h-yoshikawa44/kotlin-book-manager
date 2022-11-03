package com.book.manager.presentation.config

import com.book.manager.domain.enum.RoleType
import com.book.manager.presentation.handler.BookManagerAccessDeniedHandler
import com.book.manager.presentation.handler.BookManagerAuthenticationEntryPoint
import com.book.manager.presentation.handler.BookManagerAuthenticationFailureHandler
import com.book.manager.presentation.handler.BookManagerAuthenticationSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.authorizeRequests()
            // permitAll で未認証ユーザを含むすべてのアクセスを許可
            .mvcMatchers("/login").permitAll()
            // hasAuthority で管理者権限のユーザのみアクセスを許可
            .mvcMatchers("/admin/**").hasAuthority(RoleType.ADMIN.toString())
            // 上記以外の API で認証済みユーザ（全権限）のみアクセスを許可
            .anyRequest().authenticated()
            .and()
            .csrf().disable()
            // フォームログイン有効化
            .formLogin()
            .loginProcessingUrl("/login")
            .usernameParameter("email")
            .passwordParameter("password")
            // 認証成功時に実行するハンドラー
            .successHandler(BookManagerAuthenticationSuccessHandler())
            // 認証失敗時に実行するハンドラー
            .failureHandler(BookManagerAuthenticationFailureHandler())
            .and()
            .exceptionHandling()
            // 未認証時のハンドラー
            .authenticationEntryPoint(BookManagerAuthenticationEntryPoint())
            // 認可失敗時のハンドラー
            .accessDeniedHandler(BookManagerAccessDeniedHandler())
            // CORS 設定
            .and()
            .cors()
            .configurationSource(corsConfigurationSource())

        return http.build()
    }

    // パスワード暗号化設定
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    private fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.addAllowedMethod(CorsConfiguration.ALL)
        corsConfiguration.addAllowedHeader(CorsConfiguration.ALL)
        corsConfiguration.addAllowedOrigin("http://localhost:8081")
        corsConfiguration.allowCredentials = true

        val corsConfigurationSource = UrlBasedCorsConfigurationSource()
        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)

        return corsConfigurationSource
    }
}
