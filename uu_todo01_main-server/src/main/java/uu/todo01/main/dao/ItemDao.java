package uu.todo01.main.dao;

import uu.app.datastore.domain.PageInfo;
import uu.app.datastore.domain.PagedResult;
import uu.app.objectstore.dao.UuObjectDao;
import uu.todo01.main.abl.entity.Item;

public interface ItemDao extends UuObjectDao<Item> {

  Item setCompleted(String awid, String id, Boolean completed);

  PagedResult<Item> list(String awid, Boolean completed, PageInfo pageInfo);

  PagedResult<Item> list(String awid, String list, PageInfo pageInfo);

  PagedResult<Item> list(String awid, String list, Boolean completed, PageInfo pageInfo);
}
