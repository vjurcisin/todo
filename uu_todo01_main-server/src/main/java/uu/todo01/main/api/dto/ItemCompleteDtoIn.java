package uu.todo01.main.api.dto;

import uu.app.validation.ValidationType;

@ValidationType("itemCompleteDtoInType")
public class ItemCompleteDtoIn {

  private String item;
  private Boolean completed = true;

  public String getItem() {
    return item;
  }

  public ItemCompleteDtoIn setItem(String item) {
    this.item = item;
    return this;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public ItemCompleteDtoIn setCompleted(Boolean completed) {
    this.completed = completed;
    return this;
  }
}
