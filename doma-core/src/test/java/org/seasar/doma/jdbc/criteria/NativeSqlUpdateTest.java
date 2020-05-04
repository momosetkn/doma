package org.seasar.doma.jdbc.criteria;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.seasar.doma.internal.jdbc.mock.MockConfig;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.Sql;
import org.seasar.doma.jdbc.criteria.entity.Emp_;
import org.seasar.doma.jdbc.criteria.statement.UpdateStatement;

class NativeSqlUpdateTest {

  private final Config config = new MockConfig();

  @Test
  void set() {
    Emp_ e = new Emp_();
    UpdateStatement stmt =
        NativeSql.update(e)
            .set(
                c -> {
                  c.value(e.name, "bbb");
                  c.value(e.salary, new BigDecimal("1000"));
                });

    Sql<?> sql = stmt.asSql(config);
    assertEquals("update EMP t0_ set t0_.NAME = 'bbb', t0_.SALARY = 1000", sql.getFormattedSql());
  }

  @Test
  void where() {
    Emp_ e = new Emp_();
    UpdateStatement stmt =
        NativeSql.update(e)
            .set(
                c -> {
                  c.value(e.name, "bbb");
                  c.value(e.salary, new BigDecimal("1000"));
                })
            .where(c -> c.eq(e.id, 1));

    Sql<?> sql = stmt.asSql(config);
    assertEquals(
        "update EMP t0_ set t0_.NAME = 'bbb', t0_.SALARY = 1000 where t0_.ID = 1",
        sql.getFormattedSql());
  }
}
