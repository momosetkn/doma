package org.seasar.doma.internal.apt.meta;

public class OptionalDoubleSingleResultParameterMeta implements SingleResultParameterMeta {

  @Override
  public <R, P> R accept(CallableSqlParameterMetaVisitor<R, P> visitor, P p) {
    return visitor.visitOptionalDoubleSingleResultParameterMeta(this, p);
  }
}
