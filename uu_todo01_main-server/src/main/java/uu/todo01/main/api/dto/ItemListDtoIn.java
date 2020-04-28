package uu.todo01.main.api.dto;

import uu.app.datastore.domain.PageInfo;
import uu.app.validation.ValidationType;

@ValidationType("itemListDtoInType")
public class ItemListDtoIn {

  private String list;
  private Boolean completed;
  private PageInfo pageInfo;


  public String getList() {
    return list;
  }

  public ItemListDtoIn setList(String list) {
    this.list = list;
    return this;
  }

  public Boolean getCompleted() {
    return completed;
  }

  public ItemListDtoIn setCompleted(Boolean completed) {
    this.completed = completed;
    return this;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public ItemListDtoIn setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
    return this;
  }
}
