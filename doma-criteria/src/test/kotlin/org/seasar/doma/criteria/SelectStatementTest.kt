package org.seasar.doma.criteria

import java.math.BigDecimal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.seasar.doma.criteria.entity._Dept
import org.seasar.doma.criteria.entity._Emp
import org.seasar.doma.criteria.mock.ColumnMetaData
import org.seasar.doma.criteria.mock.MockConfig
import org.seasar.doma.criteria.mock.MockConnection
import org.seasar.doma.criteria.mock.MockPreparedStatement
import org.seasar.doma.criteria.mock.MockResultSet
import org.seasar.doma.criteria.mock.MockResultSetMetaData
import org.seasar.doma.criteria.mock.RowData

class SelectStatementTest {

    private val config = MockConfig()

    @Test
    fun eq() {
        val query = select(::_Dept) { d ->
            where {
                eq(d.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.ID = 1"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun eq_propType() {
        val query = select(::_Dept) { d ->
            val e = leftJoin(::_Emp) { e ->
                eq(d.id, e.id)
            }
            where {
                eq(d.id, e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ left outer join EMP t1_ on (t0_.ID = t1_.ID) where t0_.ID = t1_.ID"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun ne() {
        val query = select(::_Dept) { d ->
            where {
                ne(d.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.ID <> 1"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun ne_propType() {
        val query = select(::_Dept) { d ->
            val e = leftJoin(::_Emp) { e ->
                eq(d.id, e.id)
            }
            where {
                ne(d.id, e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ left outer join EMP t1_ on (t0_.ID = t1_.ID) where t0_.ID <> t1_.ID"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun ge() {
        val query = select(::_Dept) { d ->
            where {
                ge(d.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.ID >= 1"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun ge_propType() {
        val query = select(::_Dept) { d ->
            val e = leftJoin(::_Emp) { e ->
                eq(d.id, e.id)
            }
            where {
                ge(d.id, e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ left outer join EMP t1_ on (t0_.ID = t1_.ID) where t0_.ID >= t1_.ID"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun gt() {
        val query = select(::_Dept) { d ->
            where {
                gt(d.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.ID > 1"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun gt_propType() {
        val query = select(::_Dept) { d ->
            val e = leftJoin(::_Emp) { e ->
                eq(d.id, e.id)
            }
            where {
                gt(d.id, e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ left outer join EMP t1_ on (t0_.ID = t1_.ID) where t0_.ID > t1_.ID"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun le() {
        val query = select(::_Dept) { d ->
            where {
                le(d.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.ID <= 1"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun le_propType() {
        val query = select(::_Dept) { d ->
            val e = leftJoin(::_Emp) { e ->
                eq(d.id, e.id)
            }
            where {
                le(d.id, e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ left outer join EMP t1_ on (t0_.ID = t1_.ID) where t0_.ID <= t1_.ID"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun lt() {
        val query = select(::_Dept) { d ->
            where {
                lt(d.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.ID < 1"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun lt_propType() {
        val query = select(::_Dept) { d ->
            val e = leftJoin(::_Emp) { e ->
                eq(d.id, e.id)
            }
            where {
                lt(d.id, e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ left outer join EMP t1_ on (t0_.ID = t1_.ID) where t0_.ID < t1_.ID"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun like() {
        val query = select(::_Dept) { d ->
            where {
                like(d.name, "%test")
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.NAME like '%test'"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun notLike() {
        val query = select(::_Dept) { d ->
            where {
                notLike(d.name, "%test")
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME from "CATA"."DEPT" t0_ where t0_.NAME not like '%test'"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun innerJoin() {
        val query = select(::_Emp) { e ->
            innerJoin(::_Dept) { d -> eq(e.id, d.id) }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ inner join "CATA"."DEPT" t1_ on (t0_.ID = t1_.ID)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun association() {
        val query = select(::_Emp) { e ->
            val d = innerJoin(::_Dept) { d -> eq(e.id, d.id) }
            associate(e, d) { emp, dept -> }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION, t1_.ID, t1_.NAME from EMP t0_ inner join "CATA"."DEPT" t1_ on (t0_.ID = t1_.ID)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun where() {
        val query = select(::_Emp) { e ->
            where {
                eq(e.id, 1)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID = 1"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun where_and() {
        val query = select(::_Emp) { e ->
            where {
                eq(e.id, (1))
                and {
                    eq(e.name, "hoge")
                }
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID = 1 and (t0_.NAME = 'hoge')"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun `in`() {
        val query = select(::_Emp) { e ->
            where {
                `in`(e.id, listOf(1, 2, 3))
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID in (1, 2, 3)"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun in_pair() {
        val query = select(::_Emp) { e ->
            where {
                `in`(e.id to e.name, listOf(1 to "a", 2 to "b"))
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where (t0_.ID, t0_.NAME) in ((1, 'a'), (2, 'b'))"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun in_selectSingle() {
        val query = select(::_Emp) { e ->
            where {
                `in`(e.id, selectSingle({ it.id }, ::_Dept) {})
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID in (select t1_.ID from "CATA"."DEPT" t1_)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun in_selectPair() {
        val query = select(::_Emp) { e ->
            where {
                `in`(e.id to e.name, selectPair({ it.id to it.name }, ::_Dept) {})
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where (t0_.ID, t0_.NAME) in (select t1_.ID, t1_.NAME from "CATA"."DEPT" t1_)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun notIn() {
        val query = select(::_Emp) { e ->
            where {
                notIn(e.id, listOf(1, 2, 3))
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID not in (1, 2, 3)"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun notIn_pair() {
        val query = select(::_Emp) { e ->
            where {
                notIn(e.id to e.name, listOf(1 to "a", 2 to "b"))
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where (t0_.ID, t0_.NAME) not in ((1, 'a'), (2, 'b'))"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun notIn_selectSingle() {
        val query = select(::_Emp) { e ->
            where {
                notIn(e.id, selectSingle({ it.id }, ::_Dept) {})
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID not in (select t1_.ID from "CATA"."DEPT" t1_)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun notIn_selectPair() {
        val query = select(::_Emp) { e ->
            where {
                notIn(e.id to e.name, selectPair({ it.id to it.name }, ::_Dept) {})
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where (t0_.ID, t0_.NAME) not in (select t1_.ID, t1_.NAME from "CATA"."DEPT" t1_)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun between() {
        val query = select(::_Emp) { e ->
            where {
                between(e.id, 1, 5)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID between 1 and 5"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun exists() {
        val query = select(::_Emp) { e ->
            where {
                exists(::_Dept) { d ->
                    where {
                        eq(e.name, d.name)
                    }
                }
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where exists (select * from "CATA"."DEPT" t1_ where t0_.NAME = t1_.NAME)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun notExists() {
        val query = select(::_Emp) { e ->
            where {
                notExists(::_Dept) { d ->
                    where {
                        eq(e.name, d.name)
                    }
                }
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where not exists (select * from "CATA"."DEPT" t1_ where t0_.NAME = t1_.NAME)"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun orderBy() {
        val query = select(::_Emp) { e ->
            orderBy {
                desc(e.id)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = "select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ order by t0_.ID desc"
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun innerJoin_where_orderBy() {
        val query = select(::_Emp) { e ->
            val d = innerJoin(::_Dept) { d -> eq(e.id, d.id) }
            where {
                eq(e.id, 1)
                eq(d.id, 1)
            }
            orderBy {
                desc(d.name)
            }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ inner join "CATA"."DEPT" t1_ on (t0_.ID = t1_.ID) where t0_.ID = 1 and t1_.ID = 1 order by t1_.NAME desc"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun limit() {
        val query = select(::_Emp) {
            limit(10)
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ limit 10"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun offset() {
        val query = select(::_Emp) {
            offset(10)
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ offset 10"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun forUpdate() {
        val query = select(::_Emp) {
            forUpdate {}
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ for update"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun forUpdate_nowait() {
        val query = select(::_Emp) {
            forUpdate { nowait() }
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ for update nowait"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun distinct() {
        val query = select(::_Emp) {
            distinct()
        }
        val (_, sql) = query.buildContextAndSql(config)
        val expected = """select distinct t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun plus() {
        val query1 = select(::_Emp) { e ->
            where {
                eq(e.id, 1)
            }
        }
        val query2 = select(::_Emp) { e ->
            where {
                eq(e.name, "hoge")
            }
        }
        val query3 = query1 + query2
        val (_, sql) = query3.buildContextAndSql(config)
        val expected = """select t0_.ID, t0_.NAME, t0_.SALARY, t0_.VERSION from EMP t0_ where t0_.ID = 1 and t0_.NAME = 'hoge'"""
        assertEquals(expected, sql.formattedSql)
    }

    @Test
    fun execute() {
        val metaData = MockResultSetMetaData()
        metaData.columns.add(ColumnMetaData("id"))
        metaData.columns.add(ColumnMetaData("name"))
        metaData.columns.add(ColumnMetaData("salary"))
        metaData.columns.add(ColumnMetaData("version"))
        val resultSet = MockResultSet(metaData)
        resultSet.rows.add(RowData(1, "hoge", BigDecimal(10000), 100))
        resultSet.rows.add(RowData(2, "foo", BigDecimal(20000), 200))
        resultSet.rows.add(RowData(3, "bar", BigDecimal(30000), 300))
        config.dataSource.connection = MockConnection(MockPreparedStatement(resultSet))

        val query = select(::_Emp) {}
        val list = query.execute(config)
        assertEquals(3, list.size)

        var emp = list[0]
        assertEquals(1, emp.id)
        assertEquals("hoge", emp.name)
        assertEquals(0, BigDecimal(10000).compareTo(emp.salary))
        assertEquals(100, emp.version)

        emp = list[1]
        assertEquals(2, emp.id)
        assertEquals("foo", emp.name)
        assertEquals(0, BigDecimal(20000).compareTo(emp.salary))
        assertEquals(200, emp.version)

        emp = list[2]
        assertEquals(3, emp.id)
        assertEquals("bar", emp.name)
        assertEquals(0, BigDecimal(30000).compareTo(emp.salary))
        assertEquals(300, emp.version)
    }
}
