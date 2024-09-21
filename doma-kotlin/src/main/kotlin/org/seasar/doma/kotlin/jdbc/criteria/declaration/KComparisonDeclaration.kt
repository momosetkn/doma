package org.seasar.doma.kotlin.jdbc.criteria.declaration

import org.seasar.doma.jdbc.criteria.metamodel.PropertyMetamodel
import org.seasar.doma.kotlin.jdbc.criteria.DeprecatedMessages.CAN_AUTO_REPLACE

abstract class KComparisonDeclaration<DECLARATION : org.seasar.doma.jdbc.criteria.declaration.ComparisonDeclaration>(
    protected val declaration: DECLARATION
) {

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
        ReplaceWith("propertyMetamodel.isNull()", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> isNull(propertyMetamodel: PropertyMetamodel<PROPERTY>) {
        declaration.isNull(propertyMetamodel)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("propertyMetamodel.isNotNull()", "org.komapper.core.dsl.scope.FilterScope"),
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

    fun and(block: () -> Unit) {
        declaration.and(block)
    }

    fun or(block: () -> Unit) {
        declaration.or(block)
    }

    fun not(block: () -> Unit) {
        declaration.not(block)
    }
}
