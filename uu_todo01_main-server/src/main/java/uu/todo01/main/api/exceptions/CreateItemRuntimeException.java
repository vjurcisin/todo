package uu.todo01.main.api.exceptions;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class CreateItemRuntimeException extends AppRuntimeException {

  private static final String ERROR_PREFIX = "uu-todo01-main/item/create";

  public CreateItemRuntimeException(CreateItemRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public CreateItemRuntimeException(CreateItemRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public CreateItemRuntimeException(CreateItemRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application(ERROR_PREFIX + "/invalidDtoIn"), "DtoIn is not valid."),
    LIST_DOES_NOT_EXIST(ErrorCode.application(ERROR_PREFIX + "/listDoesNotExist"), "List with given id does not exist."),
    ITEM_DAO_CREATE_FAILED(ErrorCode.application(ERROR_PREFIX + "/itemDaoCreateFailed"), "Add item by item DAO create failed.");

    private ErrorCode code;

    private String message;

    Error(ErrorCode code, String message) {
      this.code = code;
      this.message = message;
    }

    public ErrorCode getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }

  }

}
