package org.seasar.doma.internal.apt.dao;

import org.seasar.doma.Dao;
import org.seasar.doma.In;
import org.seasar.doma.Procedure;
import org.seasar.doma.jdbc.Reference;

@Dao(config = MyConfig.class)
public interface ReferenceRawTypeParamDao {

  @SuppressWarnings("rawtypes")
  @Procedure
  void select(@In Reference<Height> height);
}
