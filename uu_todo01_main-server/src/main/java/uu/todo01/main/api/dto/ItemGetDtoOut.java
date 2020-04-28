package uu.todo01.main.api.dto;

import uu.app.dto.AbstractDtoOut;
import uu.todo01.main.abl.entity.Item;

public class ItemGetDtoOut extends AbstractDtoOut {

  private Item item;

  public Item getItem() {
    return item;
  }

  public ItemGetDtoOut setItem(Item item) {
    this.item = item;
    return this;
  }
}
