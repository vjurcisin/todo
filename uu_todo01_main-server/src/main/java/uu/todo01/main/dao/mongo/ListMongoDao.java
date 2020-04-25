package uu.todo01.main.dao.mongo;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.index.Index;
import uu.app.objectstore.annotations.ObjectStoreDao;
import uu.app.objectstore.mongodb.dao.UuObjectMongoDao;
import uu.todo01.main.abl.entity.List;
import uu.todo01.main.dao.ListDao;

@ObjectStoreDao(entity = List.class, store = "primary", maxNoi = 100)
public class ListMongoDao extends UuObjectMongoDao<List> implements ListDao {

  @Override
  public void createSchema() {
    super.createSchema();
    createIndex(new Index()
      .on(ATTR_AWID, Direction.ASC)
      .on(ATTR_ID, Direction.ASC)
      .unique());
  }
}
