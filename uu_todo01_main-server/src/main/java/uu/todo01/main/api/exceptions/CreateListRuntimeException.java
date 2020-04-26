package uu.todo01.main.api.exceptions;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class CreateListRuntimeException extends AppRuntimeException {

  private static final String ERROR_PREFIX = "uu-todo01-main/list/create";

  public CreateListRuntimeException(CreateListRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public CreateListRuntimeException(CreateListRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public CreateListRuntimeException(CreateListRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application(ERROR_PREFIX + "/invalidDtoIn"), "DtoIn is not valid."),
    LIST_DAO_CREATE_FAILED(ErrorCode.application(ERROR_PREFIX + "/listDaoCreateFailed"), "Add list by list DAO create failed.");

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
