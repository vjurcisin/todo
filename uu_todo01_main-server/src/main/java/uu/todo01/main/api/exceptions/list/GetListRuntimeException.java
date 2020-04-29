package uu.todo01.main.api.exceptions.list;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class GetListRuntimeException extends AppRuntimeException {

  private static final String ERROR_PREFIX = "uu-todo01-main/list/get";

  public GetListRuntimeException(GetListRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public GetListRuntimeException(GetListRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public GetListRuntimeException(GetListRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application(ERROR_PREFIX + "/invalidDtoIn"), "DtoIn is not valid."),
    LIST_DOES_NOT_EXIST(ErrorCode.application(ERROR_PREFIX + "/listDoesNotExist"), "List with given id does not exist."),
    LIST_DAO_GET_FAILED(ErrorCode.application(ERROR_PREFIX + "/listDaoGetFailed"), "List by list DAO Get failed.");

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
