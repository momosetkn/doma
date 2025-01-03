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
package org.seasar.doma.internal.apt.meta.query;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import org.seasar.doma.internal.apt.AptException;
import org.seasar.doma.internal.apt.Context;
import org.seasar.doma.internal.apt.annot.ProcedureAnnot;
import org.seasar.doma.message.Message;

public class AutoProcedureQueryMetaFactory
    extends AutoModuleQueryMetaFactory<AutoProcedureQueryMeta> {

  public AutoProcedureQueryMetaFactory(
      Context ctx, TypeElement daoElement, ExecutableElement methodElement) {
    super(ctx, daoElement, methodElement);
  }

  @Override
  public QueryMeta createQueryMeta() {
    ProcedureAnnot procedureAnnot = ctx.getAnnotations().newProcedureAnnot(methodElement);
    if (procedureAnnot == null) {
      return null;
    }
    AutoProcedureQueryMeta queryMeta = new AutoProcedureQueryMeta(daoElement, methodElement);
    queryMeta.setQueryKind(QueryKind.AUTO_PROCEDURE);
    queryMeta.setProcedureAnnot(procedureAnnot);
    doTypeParameters(queryMeta);
    doReturnType(queryMeta);
    doParameters(queryMeta);
    doThrowTypes(queryMeta);
    return queryMeta;
  }

  @Override
  protected void doReturnType(AutoProcedureQueryMeta queryMeta) {
    QueryReturnMeta resultMeta = createReturnMeta(queryMeta);
    if (!resultMeta.isPrimitiveVoid()) {
      throw new AptException(Message.DOMA4064, methodElement, new Object[] {});
    }
    queryMeta.setReturnMeta(resultMeta);
  }
}
