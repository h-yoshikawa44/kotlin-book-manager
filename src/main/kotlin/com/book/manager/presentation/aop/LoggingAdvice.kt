package com.book.manager.presentation.aop

import com.book.manager.application.service.security.BookManagerUserDetails
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

// SLF4J ロガーの生成
private val logger = LoggerFactory.getLogger(LoggingAdvice::class.java)

@Aspect
@Component
class LoggingAdvice {
    // 前処理定義
    @Before("execution(* com.book.manager.presentation.controller..*.*(..))")
    fun beforeLog(joinPoint: JoinPoint) {
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        logger.info("Start: ${joinPoint.signature} userId=${user.id}")
        logger.info("Class: ${joinPoint.target.javaClass}")
        logger.info("Session: ${(RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session.id}")
    }

    // 後処理定義
    @After("execution(* com.book.manager.presentation.controller..*.*(..))")
    fun afterLog(joinPoint: JoinPoint) {
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        logger.info("End: ${joinPoint.signature} userId=${user.id}")
    }

    @Around("execution(* com.book.manager.presentation.controller..*.*(..))")
    fun aroundLog(joinPoint: ProceedingJoinPoint): Any? {
        // 前処理
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        logger.info("Start Proceed: ${joinPoint.signature} userId=${user.id}")

        // 本処理の実行
        val result = joinPoint.proceed()

        // 後処理
        logger.info("End Proceed: ${joinPoint.signature} userId=${user.id}")

        // 本処理の結果の返却
        return result
    }
}
