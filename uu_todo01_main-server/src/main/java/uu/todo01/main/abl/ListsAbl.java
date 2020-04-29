package uu.todo01.main.abl;


import static uu.todo01.main.api.exceptions.list.CreateListRuntimeException.Error.LIST_DAO_CREATE_FAILED;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.datastore.domain.PagedResult;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.exception.AppErrorMap;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.todo01.main.abl.entity.List;
import uu.todo01.main.api.dto.list.ListCreateDtoIn;
import uu.todo01.main.api.dto.list.ListCreateDtoOut;
import uu.todo01.main.api.dto.list.ListGetDtoIn;
import uu.todo01.main.api.dto.list.ListGetDtoOut;
import uu.todo01.main.api.dto.list.ListListDtoIn;
import uu.todo01.main.api.dto.list.ListListDtoOut;
import uu.todo01.main.api.exceptions.list.CreateListRuntimeException;
import uu.todo01.main.api.exceptions.list.GetListRuntimeException;
import uu.todo01.main.api.exceptions.list.GetListRuntimeException.Error;
import uu.todo01.main.api.exceptions.list.ListListRuntimeException;
import uu.todo01.main.dao.ListDao;

@Component
public class ListsAbl {

  private static final String UNSUPPORTED_KEYS = "unsupportedKeys";

  @Inject
  private Validator validator;

  @Inject
  private ListDao listDao;

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
   * @param awid
   * @param dtoIn
   * @return
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
}
