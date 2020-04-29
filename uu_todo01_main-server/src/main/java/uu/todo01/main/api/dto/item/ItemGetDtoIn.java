package uu.todo01.main.api.dto.item;

import uu.app.validation.ValidationType;

@ValidationType("itemGetDtoInType")
public class ItemGetDtoIn {

  private String id;

  public String getId() {
    return id;
  }

  public ItemGetDtoIn setId(String id) {
    this.id = id;
    return this;
  }
}
