package org.seasar.doma.internal.apt.entity;

import org.seasar.doma.Entity;

@Entity
public class PrivatePropertyEntity {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
