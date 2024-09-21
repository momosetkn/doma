package org.seasar.doma.kotlin.jdbc.criteria.declaration

import org.seasar.doma.jdbc.criteria.context.SubSelectContext
import org.seasar.doma.jdbc.criteria.declaration.SubSelectFromDeclaration
import org.seasar.doma.jdbc.criteria.declaration.WhereDeclaration
import org.seasar.doma.jdbc.criteria.metamodel.EntityMetamodel
import org.seasar.doma.jdbc.criteria.metamodel.PropertyMetamodel
import org.seasar.doma.jdbc.criteria.option.LikeOption
import org.seasar.doma.jdbc.criteria.tuple.Tuple2
import org.seasar.doma.kotlin.jdbc.criteria.DeprecatedMessages.CANNOT_AUTO_REPLACE
import org.seasar.doma.kotlin.jdbc.criteria.DeprecatedMessages.CAN_AUTO_REPLACE

class KWhereDeclaration(declaration: WhereDeclaration) : org.seasar.doma.kotlin.jdbc.criteria.declaration.KComparisonDeclaration<WhereDeclaration>(declaration) {

    @Deprecated(
        CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/query/querydsl/select/#orderby",
        level = DeprecationLevel.ERROR
    )
    fun like(left: PropertyMetamodel<*>, right: CharSequence?, option: LikeOption) {
        declaration.like(left, right, option)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left like right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun like(left: PropertyMetamodel<*>, right: CharSequence?) {
        declaration.like(left, right)
    }

    @Deprecated(
        CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/query/querydsl/select/#orderby",
        level = DeprecationLevel.ERROR
    )
    fun notLike(left: PropertyMetamodel<*>?, right: CharSequence?, option: LikeOption) {
        declaration.notLike(left, right, option)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notLike right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun notLike(left: PropertyMetamodel<*>?, right: CharSequence?) {
        declaration.notLike(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("propertyMetamodel between start..end", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> between(
        propertyMetamodel: PropertyMetamodel<PROPERTY>,
        start: PROPERTY?,
        end: PROPERTY?,
    ) {
        declaration.between(propertyMetamodel, start, end)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left inList right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> `in`(left: PropertyMetamodel<PROPERTY>, right: List<PROPERTY>?) {
        declaration.`in`(left, right)
    }

    @Deprecated(
        CAN_AUTO_REPLACE,
        ReplaceWith("left notIn right", "org.komapper.core.dsl.scope.FilterScope"),
        level = DeprecationLevel.ERROR
    )
    fun <PROPERTY : Any> notIn(left: PropertyMetamodel<PROPERTY>, right: List<PROPERTY>?) {
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
        right: List<Tuple2<PROPERTY1, PROPERTY2>>?,
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
        right: List<Tuple2<PROPERTY1, PROPERTY2>>?,
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

    fun exists(subSelectContext: SubSelectContext<*>) {
        declaration.exists(subSelectContext)
    }

    fun notExists(subSelectContext: SubSelectContext<*>) {
        declaration.notExists(subSelectContext)
    }

    fun <ENTITY : Any> from(entityMetamodel: EntityMetamodel<ENTITY>): KSubSelectFromDeclaration<ENTITY> {
        val declaration = SubSelectFromDeclaration<ENTITY>(entityMetamodel)
        return KSubSelectFromDeclaration(declaration)
    }
}
