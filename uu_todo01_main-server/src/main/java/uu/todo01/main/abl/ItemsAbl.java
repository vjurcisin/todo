package uu.todo01.main.abl;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static uu.todo01.main.api.exceptions.GetItemRuntimeException.Error.ITEM_DAO_GET_FAILED;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import uu.app.datastore.domain.PagedResult;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.exception.AppErrorMap;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.todo01.main.abl.entity.Item;
import uu.todo01.main.api.dto.ItemCompleteDtoIn;
import uu.todo01.main.api.dto.ItemCompleteDtoOut;
import uu.todo01.main.api.dto.ItemCreateDtoIn;
import uu.todo01.main.api.dto.ItemCreateDtoOut;
import uu.todo01.main.api.dto.ItemGetDtoIn;
import uu.todo01.main.api.dto.ItemGetDtoOut;
import uu.todo01.main.api.dto.ItemListDtoIn;
import uu.todo01.main.api.dto.ItemListDtoOut;
import uu.todo01.main.api.exceptions.CompleteItemRuntimeException;
import uu.todo01.main.api.exceptions.CreateItemRuntimeException;
import uu.todo01.main.api.exceptions.CreateItemRuntimeException.Error;
import uu.todo01.main.api.exceptions.GetItemRuntimeException;
import uu.todo01.main.api.exceptions.ListItemRuntimeException;
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


  /**
   * Create item.
   * @param awid
   * @param dtoIn
   * @return
   */
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

  public boolean listExists(String awid, String list) {
    return listDao.getCount(new Query()
      .addCriteria(where(ATTR_AWID).is(awid))
      .addCriteria(where(ATTR_ID).is(list))) > 0;
  }

  /**
   * Get item.
   * @param awid
   * @param dtoIn
   * @return
   */
  public ItemGetDtoOut getItem(String awid, ItemGetDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new GetItemRuntimeException(GetItemRuntimeException.Error.INVALID_DTO_IN,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    Item item;
    try {
      item = itemDao.get(awid, dtoIn.getId());
    } catch (DatastoreRuntimeException ex) {
      throw new GetItemRuntimeException(ITEM_DAO_GET_FAILED,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    if (item == null) { // A3
      throw new GetItemRuntimeException(GetItemRuntimeException.Error.ITEM_DOES_NOT_EXIST,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    return new ItemGetDtoOut()
      .setItem(item);
  }

  /**
   * Complete item.
   * @param awid
   * @param dtoIn
   * @return
   */
  public ItemCompleteDtoOut completeItem(String awid, ItemCompleteDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new CompleteItemRuntimeException(CompleteItemRuntimeException.Error.INVALID_DTO_IN,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // HDS 1.4
    // Used default value from DTO class.

    Item item;
    try {
      item = itemDao.setCompleted(awid, dtoIn.getItem(), dtoIn.getCompleted());
    } catch (DatastoreRuntimeException ex) {  // A3
      throw new CompleteItemRuntimeException(CompleteItemRuntimeException.Error.ITEM_DAO_SET_COMPLETED_FAILED,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    return new ItemCompleteDtoOut()
      .setItem(item);
  }

  /**
   * List item.
   * @param awid
   * @param dtoIn
   * @return
   */
  public ItemListDtoOut listItem(String awid, ItemListDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);
    AppErrorMap errorMap = new AppErrorMap();

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new ListItemRuntimeException(ListItemRuntimeException.Error.INVALID_DTO_IN,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // HDS 2
    PagedResult<Item> pagedResult;
    try {
      if (dtoIn.getList() != null && dtoIn.getCompleted() != null) {  // HDS 2.1
        pagedResult = itemDao.list(awid, dtoIn.getList(), dtoIn.getCompleted(), dtoIn.getPageInfo());
      } else if (dtoIn.getList() != null) {
        pagedResult = itemDao.list(awid, dtoIn.getList(), dtoIn.getPageInfo());
      } else if (dtoIn.getCompleted() != null) {
        pagedResult = itemDao.list(awid, dtoIn.getCompleted(), dtoIn.getPageInfo());
      } else {
        pagedResult = itemDao.list(awid, dtoIn.getPageInfo());
      }
    } catch (DatastoreRuntimeException ex) {
      throw new ListItemRuntimeException(ListItemRuntimeException.Error.ITEM_DAO_LIST_ITEMS_FAILED,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    final ItemListDtoOut dtoOut = modelMapper.map(pagedResult, ItemListDtoOut.class);
    dtoOut.setUuAppErrorMap(errorMap);
    return dtoOut;
  }
}
