package uu.todo01.main.abl.entity;

import uu.app.objectstore.mongodb.domain.AbstractUuObject;

public class Item extends AbstractUuObject {

  private String list;
  private String text;
  private Boolean completed;

  public String getList() {
    return list;
  }

  public Item setList(String list) {
    this.list = list;
    return this;
  }

  public String getText() {
    return text;
  }

  public Item setText(String text) {
    this.text = text;
    return this;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public Item setCompleted(Boolean completed) {
    this.completed = completed;
    return this;
  }
}
