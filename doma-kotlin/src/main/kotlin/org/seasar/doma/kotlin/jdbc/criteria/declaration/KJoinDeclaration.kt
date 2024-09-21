package org.seasar.doma.kotlin.jdbc.criteria.declaration

import org.seasar.doma.jdbc.criteria.context.SubSelectContext
import org.seasar.doma.jdbc.criteria.declaration.JoinDeclaration
import org.seasar.doma.jdbc.criteria.declaration.SubSelectFromDeclaration
import org.seasar.doma.jdbc.criteria.metamodel.EntityMetamodel
import org.seasar.doma.jdbc.criteria.metamodel.PropertyMetamodel
import org.seasar.doma.jdbc.criteria.option.LikeOption
import org.seasar.doma.jdbc.criteria.tuple.Tuple2
import org.seasar.doma.jdbc.criteria.tuple.Tuple3
import org.seasar.doma.kotlin.jdbc.criteria.DeprecatedMessages.CANNOT_AUTO_REPLACE
import org.seasar.doma.kotlin.jdbc.criteria.DeprecatedMessages.CAN_AUTO_REPLACE

class KJoinDeclaration(private val declaration: JoinDeclaration) {
    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left eq right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> eq(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.eq(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left eq right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> eq(left: PropertyMetamodel<PROPERTY>, right: PropertyMetamodel<PROPERTY>) {
        declaration.eq(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left ne right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> ne(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.ne(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left ne right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> ne(left: PropertyMetamodel<PROPERTY>, right: PropertyMetamodel<PROPERTY>) {
        declaration.ne(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left gt right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> gt(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.gt(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left gt right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> gt(left: PropertyMetamodel<PROPERTY>, right: PropertyMetamodel<PROPERTY>) {
        declaration.gt(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left ge right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> ge(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.ge(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left ge right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> ge(left: PropertyMetamodel<PROPERTY>, right: PropertyMetamodel<PROPERTY>) {
        declaration.ge(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left lt right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> lt(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.lt(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left lt right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> lt(left: PropertyMetamodel<PROPERTY>, right: PropertyMetamodel<PROPERTY>) {
        declaration.lt(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left le right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> le(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.le(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left le right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> le(left: PropertyMetamodel<PROPERTY>, right: PropertyMetamodel<PROPERTY>) {
        declaration.le(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("isNull(propertyMetamodel)", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> isNull(propertyMetamodel: PropertyMetamodel<PROPERTY>) {
        declaration.isNull(propertyMetamodel)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("isNotNull(propertyMetamodel)", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> isNotNull(propertyMetamodel: PropertyMetamodel<PROPERTY>) {
        declaration.isNotNull(propertyMetamodel)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left eqOrIsNull right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> eqOrIsNull(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.eqOrIsNull(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left neOrIsNotNull right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> neOrIsNotNull(left: PropertyMetamodel<PROPERTY>, right: PROPERTY?) {
        declaration.neOrIsNotNull(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left like right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> like(left: PropertyMetamodel<PROPERTY>, right: CharSequence) {
        declaration.like(left, right)
    }

    @Deprecated(
        CANNOT_AUTO_REPLACE+"https://www.komapper.org/docs/reference/query/querydsl/select/#options",
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> like(left: PropertyMetamodel<PROPERTY>, right: CharSequence, option: LikeOption) {
        declaration.like(left, right, option)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notLike right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> notLike(left: PropertyMetamodel<PROPERTY>, right: CharSequence) {
        declaration.notLike(left, right)
    }

    @Deprecated(
        CANNOT_AUTO_REPLACE+"https://www.komapper.org/docs/reference/query/querydsl/select/#options",
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> notLike(left: PropertyMetamodel<PROPERTY>, right: CharSequence, option: LikeOption) {
        declaration.notLike(left, right, option)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("between(propertyMetamodel, start, end)", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> between(propertyMetamodel: PropertyMetamodel<PROPERTY>, start: PROPERTY?, end: PROPERTY?) {
        declaration.between(propertyMetamodel, start, end)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> `in`(left: PropertyMetamodel<PROPERTY>, right: List<PROPERTY>) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notInList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> notIn(left: PropertyMetamodel<PROPERTY>, right: List<PROPERTY>) {
        declaration.notIn(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> `in`(
        left: PropertyMetamodel<PROPERTY>,
        right: SubSelectContext<PropertyMetamodel<PROPERTY>>,
    ) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notInList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> notIn(
        left: PropertyMetamodel<PROPERTY>,
        right: SubSelectContext<PropertyMetamodel<PROPERTY>>,
    ) {
        declaration.notIn(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any> `in`(
        left: Tuple2<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>>,
        right: List<Tuple2<PROPERTY1, PROPERTY2>>,
    ) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notInList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any> notIn(
        left: Tuple2<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>>,
        right: List<Tuple2<PROPERTY1, PROPERTY2>>,
    ) {
        declaration.notIn(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any> `in`(
        left: Tuple2<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>>,
        right: SubSelectContext<Tuple2<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>>>,
    ) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notInList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any> notIn(
        left: Tuple2<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>>,
        right: SubSelectContext<Tuple2<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>>>,
    ) {
        declaration.notIn(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any, PROPERTY3 : Any> `in`(
        left: Tuple3<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>, PropertyMetamodel<PROPERTY3>>,
        right: List<Tuple3<PROPERTY1, PROPERTY2, PROPERTY3>>,
    ) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notInList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any, PROPERTY3 : Any> notIn(
        left: Tuple3<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>, PropertyMetamodel<PROPERTY3>>,
        right: List<Tuple3<PROPERTY1, PROPERTY2, PROPERTY3>>,
    ) {
        declaration.notIn(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any, PROPERTY3 : Any> `in`(
        left: Tuple3<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>, PropertyMetamodel<PROPERTY3>>,
        right: SubSelectContext<Tuple3<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>, PropertyMetamodel<PROPERTY3>>>,
    ) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notInList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY1 : Any, PROPERTY2 : Any, PROPERTY3 : Any> notIn(
        left: Tuple3<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>, PropertyMetamodel<PROPERTY3>>,
        right: SubSelectContext<Tuple3<PropertyMetamodel<PROPERTY1>, PropertyMetamodel<PROPERTY2>, PropertyMetamodel<PROPERTY3>>>,
    ) {
        declaration.notIn(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("exists(subSelectContext)", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun exists(
        subSelectContext: SubSelectContext<*>,
    ) {
        declaration.exists(subSelectContext)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("notExists(subSelectContext)", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun notExists(
        subSelectContext: SubSelectContext<*>,
    ) {
        declaration.notExists(subSelectContext)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("from(entityMetamodel)", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <ENTITY : Any> from(
        entityMetamodel: EntityMetamodel<ENTITY>,
    ): SubSelectFromDeclaration<ENTITY> {
        return declaration.from(entityMetamodel)
    }

    fun and(
        block: KJoinDeclaration.() -> Unit,
    ) {
        return declaration.and {
            block()
        }
    }

    fun or(
        block: KJoinDeclaration.() -> Unit,
    ) {
        return declaration.or {
            block()
        }
    }

    fun not(
        block: KJoinDeclaration.() -> Unit,
    ) {
        return declaration.not {
            block()
        }
    }
}
