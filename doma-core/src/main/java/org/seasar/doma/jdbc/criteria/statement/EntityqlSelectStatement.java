package org.seasar.doma.jdbc.criteria.statement;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.seasar.doma.def.EntityDef;
import org.seasar.doma.jdbc.Config;
import org.seasar.doma.jdbc.PreparedSql;
import org.seasar.doma.jdbc.SqlLogType;
import org.seasar.doma.jdbc.command.Command;
import org.seasar.doma.jdbc.criteria.command.AssociateCommand;
import org.seasar.doma.jdbc.criteria.context.SelectContext;
import org.seasar.doma.jdbc.criteria.declaration.JoinDeclaration;
import org.seasar.doma.jdbc.criteria.declaration.OrderByDeclaration;
import org.seasar.doma.jdbc.criteria.declaration.SelectFromDeclaration;
import org.seasar.doma.jdbc.criteria.declaration.WhereDeclaration;
import org.seasar.doma.jdbc.criteria.query.CriteriaQuery;
import org.seasar.doma.jdbc.criteria.query.SelectBuilder;

public class EntityqlSelectStatement<ENTITY> extends AbstractStatement<List<ENTITY>>
    implements SelectStatement<ENTITY> {

  private final SelectFromDeclaration declaration;

  public EntityqlSelectStatement(SelectFromDeclaration declaration) {
    Objects.requireNonNull(declaration);
    this.declaration = declaration;
  }

  public EntityqlSelectStatement<ENTITY> innerJoin(
      EntityDef<?> entityDef, Consumer<JoinDeclaration> block) {
    declaration.innerJoin(entityDef, block);
    return this;
  }

  public EntityqlSelectStatement<ENTITY> leftJoin(
      EntityDef<?> entityDef, Consumer<JoinDeclaration> block) {
    declaration.leftJoin(entityDef, block);
    return this;
  }

  public <ENTITY1, ENTITY2> EntityqlSelectStatement<ENTITY> associate(
      EntityDef<ENTITY1> first,
      EntityDef<ENTITY2> second,
      BiConsumer<ENTITY1, ENTITY2> associator) {
    declaration.associate(first, second, associator);
    return this;
  }

  public EntityqlSelectStatement<ENTITY> where(Consumer<WhereDeclaration> block) {
    declaration.where(block);
    return this;
  }

  public EntityqlSelectStatement<ENTITY> orderBy(Consumer<OrderByDeclaration> block) {
    declaration.orderBy(block);
    return this;
  }

  public EntityqlSelectStatement<ENTITY> limit(int limit) {
    declaration.limit(limit);
    return this;
  }

  public EntityqlSelectStatement<ENTITY> offset(int offset) {
    declaration.offset(offset);
    return this;
  }

  public EntityqlSelectStatement<ENTITY> forUpdate() {
    declaration.forUpdate();
    return this;
  }

  public EntityqlSelectStatement<ENTITY> forUpdate(boolean nowait) {
    declaration.forUpdate(nowait);
    return this;
  }

  @Override
  protected Command<List<ENTITY>> createCommand(
      Config config, Function<String, String> commenter, SqlLogType sqlLogType) {
    SelectContext context = declaration.getContext();
    SelectBuilder builder = new SelectBuilder(config, context, commenter, sqlLogType);
    PreparedSql sql = builder.build();
    CriteriaQuery query = new CriteriaQuery(config, sql, getClass().getName(), executeMethodName);
    return new AssociateCommand<>(context, query);
  }
}
