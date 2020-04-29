package uu.todo01.main.dao.mongo;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.index.Index;
import uu.app.objectstore.annotations.ObjectStoreDao;
import uu.app.objectstore.mongodb.dao.UuObjectMongoDao;
import uu.todo01.main.abl.entity.Todo01Main;
import uu.todo01.main.dao.Todo01MainDao;

@ObjectStoreDao(entity = Todo01Main.class, store = "primary")
public class Todo01MainMongoDao extends UuObjectMongoDao<Todo01Main> implements Todo01MainDao {

 public void createSchema() {
   super.createSchema();
   createIndex(new Index().on(ATTR_AWID, Direction.ASC).unique());
 }

}
