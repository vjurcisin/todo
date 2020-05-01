package uu.todo01.main.api.exceptions.list;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class DeleteListRuntimeException extends AppRuntimeException {

  private static final String ERROR_PREFIX = "uu-todo01-main/list/delete";

  public DeleteListRuntimeException(DeleteListRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public DeleteListRuntimeException(DeleteListRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public DeleteListRuntimeException(DeleteListRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application(ERROR_PREFIX + "/invalidDtoIn"), "DtoIn is not valid."),
    LIST_NOT_EMPTY(ErrorCode.application(ERROR_PREFIX + "/listNotEmpty"), "The list contains items."),
    ITEM_DAO_DELETE_FAILED(ErrorCode.application(ERROR_PREFIX + "/itemDaoDeleteFailed"), "Delete item by item DAO delete failed."),
    LIST_DAO_DELETE_FAILED(ErrorCode.application(ERROR_PREFIX + "/listDaoDeleteFailed"), "Delete list by list DAO delete failed.");

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
