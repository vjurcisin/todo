package uu.todo01.main.api.dto.list;

import uu.app.datastore.domain.PageInfo;
import uu.app.validation.ValidationType;

@ValidationType("listListDtoInType")
public class ListListDtoIn {

  private PageInfo pageInfo;

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public ListListDtoIn setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
    return this;
  }
}
