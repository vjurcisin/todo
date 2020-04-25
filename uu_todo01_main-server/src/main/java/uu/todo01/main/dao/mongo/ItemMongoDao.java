package uu.todo01.main.dao.mongo;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import uu.app.datastore.domain.PageInfo;
import uu.app.datastore.domain.PagedResult;
import uu.app.objectstore.annotations.ObjectStoreDao;
import uu.app.objectstore.mongodb.dao.UuObjectMongoDao;
import uu.todo01.main.abl.entity.Item;
import uu.todo01.main.dao.ItemDao;

@ObjectStoreDao(entity = Item.class, store = "primary", maxNoi = 500000)
public class ItemMongoDao extends UuObjectMongoDao<Item> implements ItemDao {

  private static final String COMPLETED = "completed";
  private static final String LIST = "list";

  @Override
  public void createSchema() {
    super.createSchema();
    createIndex(new Index()
      .on(ATTR_AWID, Direction.ASC)
      .on(ATTR_ID, Direction.ASC)
      .unique());

    createIndex(new Index()
      .on(ATTR_AWID, Direction.ASC)
      .on(LIST, Direction.ASC)
      .on(COMPLETED, Direction.ASC));
  }

  @Override
  public Item setCompleted(String awid, String id, Boolean completed) {
    Query query = new Query()
      .addCriteria(Criteria.where(ATTR_AWID).is(awid))
      .addCriteria(Criteria.where(ATTR_ID).is(id));

    return this.findOneAndUpdate(query, Update.update("completed", completed));
  }

  @Override
  public PagedResult<Item> list(String awid, String list, PageInfo pageInfo) {
    Query query = new Query()
      .addCriteria(Criteria.where(ATTR_AWID).is(awid))
      .addCriteria(Criteria.where(LIST).is(list));

    return find(query, pageInfo);
  }

  @Override
  public PagedResult<Item> list(String awid, String list, Boolean completed, PageInfo pageInfo) {
    Query query = new Query()
      .addCriteria(Criteria.where(ATTR_AWID).is(awid))
      .addCriteria(Criteria.where(COMPLETED).is(completed))
      .addCriteria(Criteria.where(LIST).is(list));

    return find(query, pageInfo);
  }
}
