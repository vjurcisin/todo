package uu.todo01.main.api.dto.list;

import uu.app.validation.ValidationType;

@ValidationType("listCreateDtoInType")
public class ListCreateDtoIn {

  private String name;

  public String getName() {
    return name;
  }

  public ListCreateDtoIn setName(String name) {
    this.name = name;
    return this;
  }
}
