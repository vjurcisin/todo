package uu.todo01.main.api.exceptions;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class GetItemRuntimeException extends AppRuntimeException {

  private static final String ERROR_PREFIX = "uu-todo01-main/item/get";

  public GetItemRuntimeException(GetItemRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public GetItemRuntimeException(GetItemRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public GetItemRuntimeException(GetItemRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application(ERROR_PREFIX + "/invalidDtoIn"), "DtoIn is not valid."),
    ITEM_DOES_NOT_EXIST(ErrorCode.application(ERROR_PREFIX + "/itemDoesNotExist"), "Item with given id does not exist.");

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
