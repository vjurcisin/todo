package uu.todo01.main.api.dto.item;

import uu.app.validation.ValidationType;

@ValidationType("itemCreateDtoInType")
public class ItemCreateDtoIn {

  private String list;
  private String text;

  public String getList() {
    return list;
  }

  public ItemCreateDtoIn setList(String list) {
    this.list = list;
    return this;
  }

  public String getText() {
    return text;
  }

  public ItemCreateDtoIn setText(String text) {
    this.text = text;
    return this;
  }
}
