package uu.todo01.main.abl;


import static uu.todo01.main.api.exceptions.CreateListRuntimeException.Error.INVALID_DTO_IN;
import static uu.todo01.main.api.exceptions.CreateListRuntimeException.Error.LIST_DAO_CREATE_FAILED;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.datastore.exceptions.DatastoreRuntimeException;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.todo01.main.abl.entity.List;
import uu.todo01.main.api.dto.ListCreateDtoIn;
import uu.todo01.main.api.dto.ListCreateDtoOut;
import uu.todo01.main.api.exceptions.CreateListRuntimeException;
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
      throw new CreateListRuntimeException(INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    try {
      listDao.create(list);
    } catch (DatastoreRuntimeException ex) {  // A4
      throw new CreateListRuntimeException(LIST_DAO_CREATE_FAILED, null, ex);
    }

    return dtoOut;
  }

}
