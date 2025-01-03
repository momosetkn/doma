/*
 * Copyright Doma Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.seasar.doma.jdbc.criteria.context;

import java.util.Objects;
import org.seasar.doma.jdbc.criteria.metamodel.EntityMetamodel;
import org.seasar.doma.jdbc.criteria.statement.SetOperand;

public record WithContext(
    EntityMetamodel<?> entityMetamodel, SetOperationContext<?> setOperationContext) {
  public WithContext(
      EntityMetamodel<?> entityMetamodel, SetOperationContext<?> setOperationContext) {
    this.entityMetamodel = Objects.requireNonNull(entityMetamodel);
    this.setOperationContext = Objects.requireNonNull(setOperationContext);
  }

  public WithContext(EntityMetamodel<?> entityMetamodel, SetOperand<?> setOperand) {
    this(entityMetamodel, setOperand.getContext());
  }
}
