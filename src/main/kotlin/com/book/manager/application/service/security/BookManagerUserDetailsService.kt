package com.book.manager.application.service.security

import com.book.manager.application.service.AuthenticationService
import com.book.manager.domain.enum.RoleType
import com.book.manager.domain.model.UserModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class BookManagerUserDetailsService(
    private val authenticationService: AuthenticationService
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = authenticationService.findUser(username)
        return user?.let { BookManagerUserDetails(user) }
    }
}

// ログイン時に入力した値から取得したユーザデータを格納し、認証処理で使用されるもの
data class BookManagerUserDetails(val id: Long, val email: String, val pass: String, val roleType: RoleType) :
    UserDetails {

    constructor(user: UserModel) : this(user.id, user.email, user.password, user.roleType)

    // 権限の取得（複数の権限の保持することも可能）
    // 認可が必要なパスの場合、この関数で取得した権限の情報でチェックされる
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return AuthorityUtils.createAuthorityList(this.roleType.toString())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.email
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    // パスワードの取得。ログイン時に入力したパスワードとの比較に使用される
    override fun getPassword(): String {
        return this.pass
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}
