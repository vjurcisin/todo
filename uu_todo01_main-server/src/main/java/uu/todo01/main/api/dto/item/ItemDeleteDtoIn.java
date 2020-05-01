package uu.todo01.main.api.dto.item;

import uu.app.validation.ValidationType;

@ValidationType("itemDeleteDtoInType")
public class ItemDeleteDtoIn {

  private String id;

  public String getId() {
    return id;
  }

  public ItemDeleteDtoIn setId(String id) {
    this.id = id;
    return this;
  }
}
