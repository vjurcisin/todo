package uu.todo01.main.api.dto.list;

import uu.app.validation.ValidationType;

@ValidationType("listUpdateDtoInType")
public class ListUpdateDtoIn {

  private String list;
  private String name;

  public String getList() {
    return list;
  }

  public ListUpdateDtoIn setList(String list) {
    this.list = list;
    return this;
  }

  public String getName() {
    return name;
  }

  public ListUpdateDtoIn setName(String name) {
    this.name = name;
    return this;
  }
}
