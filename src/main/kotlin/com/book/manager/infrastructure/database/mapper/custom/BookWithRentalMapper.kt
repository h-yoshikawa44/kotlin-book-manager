package com.book.manager.infrastructure.database.mapper.custom

import com.book.manager.infrastructure.database.mapper.BookDynamicSqlSupport.book
import com.book.manager.infrastructure.database.mapper.RentalDynamicSqlSupport.rental
import com.book.manager.infrastructure.database.record.custom.BookWithRental
import org.apache.ibatis.annotations.*
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

    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @ResultMap("BookWithRentalRecordResult")
    fun selectOne(selectStatement: SelectStatementProvider): BookWithRental?
}

private val columnList = listOf(
    book.id,
    book.title,
    book.author,
    book.releaseDate,
    rental.userId,
    rental.rentalDatetime,
    rental.returnDeadline
)

/**
 * Book と Rental を JOIN する Mapper
 */
fun BookWithRentalMapper.select(): List<BookWithRental> {
    // MyBatis Dynamic SQL でクエリを書く
    val selectStatement = select(columnList)
        .from(book, "b")
        .leftJoin(rental, "r").on(book.id, equalTo(rental.bookId))
        .build()
        .render(RenderingStrategies.MYBATIS3)
    return selectMany(selectStatement)
}

fun BookWithRentalMapper.selectByPrimaryKey(id_: Long): BookWithRental? {
    val selectStatement = select(columnList)
        .from(book, "b")
        .leftJoin(rental, "r").on(book.id, equalTo(rental.bookId))
        .where(book.id, isEqualTo(id_))
        .build()
        .render(RenderingStrategies.MYBATIS3)
    return selectOne(selectStatement)
}
