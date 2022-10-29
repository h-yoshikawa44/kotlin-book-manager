package com.book.manager.infrastructure.database.mapper.custom

import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport
import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.author
import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.id
import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.releaseDate
import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.title
import com.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport
import com.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.rentalDatetime
import com.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.returnDeadline
import com.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.userId
import com.book.manager.infrastructure.database.record.custom.BookWithRental
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.type.JdbcType
import org.mybatis.dynamic.sql.SqlBuilder.*
import org.mybatis.dynamic.sql.render.RenderingStrategies
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.SqlProviderAdapter

@Mapper
interface BookWithRentalMapper {
    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @Results(
        id = "BookWithRentalRecordResult", value = [
            Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            Result(column = "author", property = "author", jdbcType = JdbcType.VARCHAR),
            Result(column = "release_date", property = "releaseDate", jdbcType = JdbcType.DATE),
            Result(column = "user_id", property = "userId", jdbcType = JdbcType.BIGINT),
            Result(column = "rental_datetime", property = "rentalDatetime", jdbcType = JdbcType.TIMESTAMP),
            Result(column = "return_deadline", property = "returnDeadline", jdbcType = JdbcType.TIMESTAMP)
        ]
    )
    // SelectStatementProvider 型の値は、MyBatis Dynamic SQL を使用して組み立てたクエリの情報を保持しているオブジェクト
    fun selectMany(selectStatement: SelectStatementProvider): List<BookWithRental>
}

private val columnList = listOf(
    id,
    title,
    author,
    releaseDate,
    userId,
    rentalDatetime,
    returnDeadline
)

/**
 * Book と Rental を JOIN する Mapper
 */
fun BookWithRentalMapper.select(): List<BookWithRental> {
    // MyBatis Dynamic SQL でクエリを書く
    val selectStatement = select(columnList)
        .from(BookDynamicSqlSupport.book, "b")
        .leftJoin(RentalDynamicSqlSupport.rental, "r").on(BookDynamicSqlSupport.id, equalTo(RentalDynamicSqlSupport.bookId))
        .build()
        .render(RenderingStrategies.MYBATIS3)
    return selectMany(selectStatement)
}
