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
package org.seasar.doma.internal.apt.processor.dao;

import org.seasar.doma.Entity;

@Entity
public class Issue214Entity extends Issue214AbstractEntity2 {

  public static final String STATIC_FIELD = "";

  public String instanceField = "";

  public static String staticMethod() {
    return null;
  }

  @Override
  public String instanceMethod() {
    return null;
  }
}
