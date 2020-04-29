package uu.todo01.main.api.dto.item;

import uu.app.dto.AbstractDtoOut;
import uu.todo01.main.abl.entity.Item;

public class ItemCreateDtoOut extends AbstractDtoOut {

  private Item item;

  public Item getItem() {
    return item;
  }

  public ItemCreateDtoOut setItem(Item item) {
    this.item = item;
    return this;
  }
}
