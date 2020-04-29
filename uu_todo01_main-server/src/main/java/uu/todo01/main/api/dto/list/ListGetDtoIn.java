package uu.todo01.main.api.dto.list;

import uu.app.validation.ValidationType;

@ValidationType("listGetDtoInType")
public class ListGetDtoIn {

  private String id;

  public String getId() {
    return id;
  }

  public ListGetDtoIn setId(String id) {
    this.id = id;
    return this;
  }
}
