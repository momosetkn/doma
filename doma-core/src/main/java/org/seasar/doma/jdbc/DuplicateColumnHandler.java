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
package org.seasar.doma.jdbc;

import org.seasar.doma.jdbc.query.Query;

/** A handler for the column that is duplicated in a result set. */
public interface DuplicateColumnHandler {

  /**
   * Handles the duplicate column.
   *
   * @param query the query
   * @param duplicateColumnName the name of the unknown column
   */
  default void handle(Query query, String duplicateColumnName) {}
}
