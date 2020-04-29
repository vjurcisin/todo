package uu.todo01.main.api.dto.list;

import uu.app.dto.AbstractDtoOut;
import uu.todo01.main.abl.entity.List;

public class ListGetDtoOut extends AbstractDtoOut {

  private List list;

  public List getList() {
    return list;
  }

  public ListGetDtoOut setList(List list) {
    this.list = list;
    return this;
  }
}
