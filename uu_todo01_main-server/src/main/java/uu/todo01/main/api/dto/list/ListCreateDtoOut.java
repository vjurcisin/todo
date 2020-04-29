package uu.todo01.main.api.dto.list;

import uu.app.dto.AbstractDtoOut;
import uu.todo01.main.abl.entity.List;

public class ListCreateDtoOut extends AbstractDtoOut {

  private List list;

  public List getList() {
    return list;
  }

  public ListCreateDtoOut setList(List list) {
    this.list = list;
    return this;
  }
}
