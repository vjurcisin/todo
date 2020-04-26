package uu.todo01.main.abl;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.todo01.main.abl.entity.Item;
import uu.todo01.main.api.dto.ItemCreateDtoIn;
import uu.todo01.main.api.dto.ItemCreateDtoOut;
import uu.todo01.main.api.exceptions.CreateItemRuntimeException;
import uu.todo01.main.api.exceptions.CreateItemRuntimeException.Error;
import uu.todo01.main.dao.ItemDao;
import uu.todo01.main.dao.ListDao;

@Component
public class ItemsAbl {

  private static final String ATTR_AWID = "awid";
  private static final String ATTR_ID = "_id";

  private static final String UNSUPPORTED_KEYS = "unsupportedKeys";

  @Inject
  private Validator validator;

  @Inject
  private ItemDao itemDao;

  @Inject
  private ListDao listDao;

  @Inject
  private ModelMapper modelMapper;

  public ItemCreateDtoOut createItem(
    String awid,
    ItemCreateDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    final Item item = modelMapper.map(dtoIn, Item.class);
    item.setAwid(awid);
    ItemCreateDtoOut dtoOut = new ItemCreateDtoOut()
      .setItem(item);
    // HDS 1.2
    if (!validationResult.getUnsupportedKeys().isEmpty()) {
      dtoOut.getUuAppErrorMap().addWarning(UNSUPPORTED_KEYS, "DtoIn contains unsupported keys.");   // A1
    }

    // HDS 1.3
    if (!validationResult.isValid()) {
      throw new CreateItemRuntimeException(Error.INVALID_DTO_IN,    // A2
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // HDS 2
    item.setCompleted(false);

    // // HDS 3
    if (!listExists(awid, item.getList())) {  // A3
      throw new CreateItemRuntimeException(Error.LIST_DOES_NOT_EXIST,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    try {
      itemDao.create(item);
    } catch (DatastoreRuntimeException ex) {  // A4
      throw new CreateItemRuntimeException(Error.ITEM_DAO_CREATE_FAILED, null, ex);
    }

    return dtoOut;
  }

  private boolean listExists(String awid, String list) {
    return listDao.getCount(new Query()
      .addCriteria(where(ATTR_AWID).is(awid))
      .addCriteria(where(ATTR_ID).is(list))) > 0;
  }
}
