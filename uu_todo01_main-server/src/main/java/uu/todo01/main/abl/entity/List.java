package uu.todo01.main.abl.entity;

import uu.app.objectstore.mongodb.domain.AbstractUuObject;

public class List extends AbstractUuObject {

  private String name;

  public String getName() {
    return name;
  }

  public List setName(String name) {
    this.name = name;
    return this;
  }
}
