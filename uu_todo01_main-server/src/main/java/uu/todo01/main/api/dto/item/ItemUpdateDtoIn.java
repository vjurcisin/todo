package uu.todo01.main.api.dto.item;

import uu.app.validation.ValidationType;

@ValidationType("itemUpdateDtoInType")
public class ItemUpdateDtoIn {

  private String item;
  private String list;
  private String text;

  public String getItem() {
    return item;
  }

  public ItemUpdateDtoIn setItem(String item) {
    this.item = item;
    return this;
  }

  public String getList() {
    return list;
  }

  public ItemUpdateDtoIn setList(String list) {
    this.list = list;
    return this;
  }

  public String getText() {
    return text;
  }

  public ItemUpdateDtoIn setText(String text) {
    this.text = text;
    return this;
  }
}
