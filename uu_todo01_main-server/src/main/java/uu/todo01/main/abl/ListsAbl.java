package uu.todo01.main.abl;


import static uu.todo01.main.api.exceptions.item.UpdateItemRuntimeException.Error.ITEM_DAO_UPDATE_FAILED;
import static uu.todo01.main.api.exceptions.list.CreateListRuntimeException.Error.LIST_DAO_CREATE_FAILED;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.datastore.domain.PageInfo;
import uu.app.datastore.domain.PagedResult;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.exception.AppErrorMap;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.todo01.main.abl.entity.Item;
import uu.todo01.main.abl.entity.List;
import uu.todo01.main.api.dto.list.ListCreateDtoIn;
import uu.todo01.main.api.dto.list.ListCreateDtoOut;
import uu.todo01.main.api.dto.list.ListDeleteDtoIn;
import uu.todo01.main.api.dto.list.ListDeleteDtoOut;
import uu.todo01.main.api.dto.list.ListGetDtoIn;
import uu.todo01.main.api.dto.list.ListGetDtoOut;
import uu.todo01.main.api.dto.list.ListListDtoIn;
import uu.todo01.main.api.dto.list.ListListDtoOut;
import uu.todo01.main.api.dto.list.ListUpdateDtoIn;
import uu.todo01.main.api.dto.list.ListUpdateDtoOut;
import uu.todo01.main.api.exceptions.item.UpdateItemRuntimeException;
import uu.todo01.main.api.exceptions.list.CreateListRuntimeException;
import uu.todo01.main.api.exceptions.list.DeleteListRuntimeException;
import uu.todo01.main.api.exceptions.list.GetListRuntimeException;
import uu.todo01.main.api.exceptions.list.GetListRuntimeException.Error;
import uu.todo01.main.api.exceptions.list.ListListRuntimeException;
import uu.todo01.main.api.exceptions.list.UpdateListRuntimeException;
import uu.todo01.main.dao.ItemDao;
import uu.todo01.main.dao.ListDao;

@Component
public class ListsAbl {

  private static final String UNSUPPORTED_KEYS = "unsupportedKeys";

  @Inject
  private Validator validator;

  @Inject
  private ListDao listDao;

  @Inject
  private ItemDao itemDao;

  @Inject
  private ModelMapper modelMapper;

  /**
   * Create list.
   */
  public ListCreateDtoOut createList(
    String awid,
    ListCreateDtoIn dtoIn) {

    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    final List list = modelMapper.map(dtoIn, List.class);
    list.setAwid(awid);
    ListCreateDtoOut dtoOut = new ListCreateDtoOut()
      .setList(list);

    // HDS 1.2
    if (!validationResult.getUnsupportedKeys().isEmpty()) { // A 1
      dtoOut.getUuAppErrorMap().addWarning(UNSUPPORTED_KEYS, "DtoIn contains unsupported keys.");   // A1
    }

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new CreateListRuntimeException(CreateListRuntimeException.Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    try {
      listDao.create(list);
    } catch (DatastoreRuntimeException ex) {  // A4
      throw new CreateListRuntimeException(LIST_DAO_CREATE_FAILED, null, ex);
    }

    return dtoOut;
  }

  /**
   * Get list.
   */
  public ListGetDtoOut getList(String awid, ListGetDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new GetListRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    List list;
    try {
      list = listDao.get(awid, dtoIn.getId());
    } catch (DatastoreRuntimeException ex) {
      throw new GetListRuntimeException(Error.LIST_DAO_GET_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    if (list == null) { // A3
      throw new GetListRuntimeException(Error.LIST_DOES_NOT_EXIST, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    return new ListGetDtoOut()
      .setList(list);
  }

  /**
   * List list.
   */
  public ListListDtoOut listList(String awid, ListListDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);
    AppErrorMap errorMap = new AppErrorMap();

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new ListListRuntimeException(ListListRuntimeException.Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // HDS 2
    PagedResult<List> pagedResult;
    try {
      pagedResult = listDao.list(awid, dtoIn.getPageInfo());
    } catch (DatastoreRuntimeException ex) {
      throw new ListListRuntimeException(ListListRuntimeException.Error.LIST_DAO_LIST_FAILED, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    final ListListDtoOut dtoOut = modelMapper.map(pagedResult, ListListDtoOut.class);
    dtoOut.setUuAppErrorMap(errorMap);
    return dtoOut;
  }

  /**
   * Update list.
   */
  public ListUpdateDtoOut updateList(String awid, ListUpdateDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new UpdateListRuntimeException(UpdateListRuntimeException.Error.INVALID_DTO_IN,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    final List list;
    try {
      list = listDao.get(awid, dtoIn.getList());
      if (list == null) {
        throw new UpdateItemRuntimeException(ITEM_DAO_UPDATE_FAILED,
          ValidationResultUtils.validationResultToAppErrorMap(validationResult));
      }
      list.setName(dtoIn.getName());
      listDao.update(list);

    } catch (DatastoreRuntimeException ex) {
      throw new UpdateItemRuntimeException(ITEM_DAO_UPDATE_FAILED,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    return new ListUpdateDtoOut()
      .setList(list);
  }

  /**
   * Delete list.
   */
  public ListDeleteDtoOut deleteList(String awid, ListDeleteDtoIn dtoIn) {
    // HDS 1
    // HDS 1.1
    ValidationResult validationResult = validator.validate(dtoIn);

    // HDS 1.3
    if (!validationResult.isValid()) {  // A2
      throw new DeleteListRuntimeException(DeleteListRuntimeException.Error.INVALID_DTO_IN,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }
    // HDS 1.4 default value of boolean = false.

    // HDS 2
    if (!dtoIn.getForceDelete() && hasUncompletedItems(awid, dtoIn.getId())) {
      throw new DeleteListRuntimeException(DeleteListRuntimeException.Error.LIST_NOT_EMPTY,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }
    deleteItemsInList(awid, dtoIn.getId(), validationResult);
    deleteList(awid, dtoIn.getId(), validationResult);

    return new ListDeleteDtoOut();
  }

  private boolean hasUncompletedItems(String awid, String listId) {
    return !itemDao.list(awid, listId, false, new PageInfo(0, 1000)).getItemList().isEmpty();
  }

  private void deleteItemsInList(String awid, String listId, ValidationResult validationResult) {
    try {
      final PagedResult<Item> itemsInList = itemDao.list(awid, listId, new PageInfo(0, 1000));
      itemsInList.getItemList().forEach(item -> itemDao.delete(item));
    } catch (DatastoreRuntimeException ex) {  // A4
      throw new DeleteListRuntimeException(DeleteListRuntimeException.Error.ITEM_DAO_DELETE_FAILED,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }
  }

  private void deleteList(String awid, String listId, ValidationResult validationResult) {
    try {
      final List list = listDao.get(awid, listId);
      if (list == null) {
        throw new DeleteListRuntimeException(DeleteListRuntimeException.Error.LIST_DAO_DELETE_FAILED,
          ValidationResultUtils.validationResultToAppErrorMap(validationResult));
      }
      listDao.delete(list);
    } catch (DatastoreRuntimeException ex) {  // A5
      throw new DeleteListRuntimeException(DeleteListRuntimeException.Error.LIST_DAO_DELETE_FAILED,
        ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }
  }
}
