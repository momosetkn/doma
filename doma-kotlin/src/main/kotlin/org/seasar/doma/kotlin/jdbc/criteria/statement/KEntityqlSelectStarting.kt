package org.seasar.doma.kotlin.jdbc.criteria.statement

import org.komapper.core.dsl.operator.asc
import org.komapper.core.dsl.operator.desc
import org.seasar.doma.jdbc.Sql
import org.seasar.doma.jdbc.criteria.metamodel.EntityMetamodel
import org.seasar.doma.jdbc.criteria.metamodel.PropertyMetamodel
import org.seasar.doma.jdbc.criteria.option.AssociationOption
import org.seasar.doma.jdbc.criteria.option.DistinctOption
import org.seasar.doma.jdbc.criteria.option.ForUpdateOption
import org.seasar.doma.jdbc.criteria.statement.EntityqlSelectStarting
import org.seasar.doma.kotlin.jdbc.criteria.DeprecatedMessages.CANNOT_AUTO_REPLACE
import org.seasar.doma.kotlin.jdbc.criteria.declaration.KJoinDeclaration
import org.seasar.doma.kotlin.jdbc.criteria.declaration.KOrderByNameDeclaration
import org.seasar.doma.kotlin.jdbc.criteria.declaration.KWhereDeclaration

class KEntityqlSelectStarting<ENTITY>(private val statement: EntityqlSelectStarting<ENTITY>) : KListable<ENTITY> {

    fun distinct(distinctOption: DistinctOption = DistinctOption.basic()): KEntityqlSelectStarting<ENTITY> {
        statement.distinct(distinctOption)
        return this
    }

    fun innerJoin(
        entityMetamodel: EntityMetamodel<*>,
        block: KJoinDeclaration.() -> Unit,
    ): KEntityqlSelectStarting<ENTITY> {
        statement.innerJoin(entityMetamodel) { block(KJoinDeclaration(it)) }
        return this
    }

    fun leftJoin(
        entityMetamodel: EntityMetamodel<*>,
        block: KJoinDeclaration.() -> Unit,
    ): KEntityqlSelectStarting<ENTITY> {
        statement.leftJoin(entityMetamodel) { block(KJoinDeclaration(it)) }
        return this
    }

    @Deprecated(CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/association/")
    fun <ENTITY1, ENTITY2> associate(
        first: EntityMetamodel<ENTITY1>,
        second: EntityMetamodel<ENTITY2>,
        associator: (ENTITY1, ENTITY2) -> Unit,
    ): KEntityqlSelectStarting<ENTITY> {
        statement.associate(first, second, associator, AssociationOption.mandatory())
        return this
    }

    @Deprecated(CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/association/")
    fun <ENTITY1, ENTITY2> associate(
        first: EntityMetamodel<ENTITY1>,
        second: EntityMetamodel<ENTITY2>,
        associator: (ENTITY1, ENTITY2) -> Unit,
        option: AssociationOption,
    ): KEntityqlSelectStarting<ENTITY> {
        statement.associate(first, second, associator, option)
        return this
    }

    @Deprecated(CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/association/")
    fun <ENTITY1, ENTITY2> associateWith(
        first: EntityMetamodel<ENTITY1>,
        second: EntityMetamodel<ENTITY2>,
        associator: (ENTITY1, ENTITY2) -> ENTITY1,
    ): KEntityqlSelectStarting<ENTITY> {
        statement.associateWith(first, second, associator)
        return this
    }

    @Deprecated(CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/association/")
    fun <ENTITY1, ENTITY2> associateWith(
        first: EntityMetamodel<ENTITY1>,
        second: EntityMetamodel<ENTITY2>,
        associator: (ENTITY1, ENTITY2) -> ENTITY1,
        option: AssociationOption,
    ): KEntityqlSelectStarting<ENTITY> {
        statement.associateWith(first, second, associator, option)
        return this
    }

    fun where(block: KWhereDeclaration.() -> Unit): KEntityqlSelectStarting<ENTITY> {
        statement.where { block(KWhereDeclaration(it)) }
        return this
    }

    @Deprecated(CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/query/querydsl/select/#orderby")
    fun orderBy(block: KOrderByNameDeclaration.() -> Unit): KEntityqlSelectStarting<ENTITY> {
        statement.orderBy { block(KOrderByNameDeclaration(it)) }
        return this
    }

    fun limit(limit: Int?): KEntityqlSelectStarting<ENTITY> {
        statement.limit(limit)
        return this
    }

    fun offset(offset: Int?): KEntityqlSelectStarting<ENTITY> {
        statement.offset(offset)
        return this
    }

    fun forUpdate(option: ForUpdateOption = ForUpdateOption.basic()): KEntityqlSelectStarting<ENTITY> {
        statement.forUpdate(option)
        return this
    }

    fun <RESULT> select(entityMetamodel: EntityMetamodel<RESULT>): KEntityqlSelectTerminal<RESULT> {
        val terminal = statement.select(entityMetamodel)
        return KEntityqlSelectTerminal(terminal)
    }

    fun <RESULT> selectTo(
        entityMetamodel: EntityMetamodel<RESULT>,
        vararg propertyMetamodels: PropertyMetamodel<*>,
    ): KEntityqlSelectTerminal<RESULT> {
        val terminal = statement.selectTo(entityMetamodel, *propertyMetamodels)
        return KEntityqlSelectTerminal(terminal)
    }

    override fun peek(block: (Sql<*>) -> Unit): KEntityqlSelectStarting<ENTITY> {
        statement.peek(block)
        return this
    }

    override fun execute(): List<ENTITY> {
        return statement.execute()
    }

    override fun asSql(): Sql<*> {
        return statement.asSql()
    }
}

// Extending Komapper to be compatible with doma
// orderBy
@Deprecated(CANNOT_AUTO_REPLACE + "https://www.komapper.org/docs/reference/query/querydsl/select/#orderby")
fun <ENTITY, QUERY: org.komapper.core.dsl.query.SelectQuery<ENTITY, QUERY>> org.komapper.core.dsl.query.SelectQuery<ENTITY, QUERY>.orderBy(block: KomapperSortExpressionDsl.() -> Unit){
    val dsl = KomapperSortExpressionDsl()
    block(dsl)
    this.orderBy(dsl.expressions)
}

class KomapperSortExpressionDsl {
    internal var expressions: MutableList<org.komapper.core.dsl.expression.SortExpression> = mutableListOf()
    fun asc(propertyMeta: org.komapper.core.dsl.metamodel.PropertyMetamodel<*,*,*>) {
        expressions += propertyMeta.asc()
    }
    fun desc(propertyMeta: org.komapper.core.dsl.metamodel.PropertyMetamodel<*,*,*>) {
        expressions += propertyMeta.desc()
    }
}
