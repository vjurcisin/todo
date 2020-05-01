package uu.todo01.main.api.dto.list;

import uu.app.validation.ValidationType;

@ValidationType("listDeleteDtoInType")
public class ListDeleteDtoIn {

  private String id;
  private boolean forceDelete;


  public String getId() {
    return id;
  }

  public ListDeleteDtoIn setId(String id) {
    this.id = id;
    return this;
  }

  public Boolean getForceDelete() {
    return forceDelete;
  }

  public ListDeleteDtoIn setForceDelete(Boolean forceDelete) {
    this.forceDelete = forceDelete;
    return this;
  }
}
