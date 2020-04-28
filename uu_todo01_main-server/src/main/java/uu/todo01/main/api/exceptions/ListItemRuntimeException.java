package uu.todo01.main.api.exceptions;

import java.util.Map;
import uu.app.exception.AppErrorMap;
import uu.app.exception.AppRuntimeException;
import uu.app.exception.ErrorCode;

public final class ListItemRuntimeException extends AppRuntimeException {

  private static final String ERROR_PREFIX = "uu-todo01-main/item/list";

  public ListItemRuntimeException(ListItemRuntimeException.Error code, Map<String, ?> paramMap, Throwable cause) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, cause);
  }

  public ListItemRuntimeException(ListItemRuntimeException.Error code, String message, Object... params) {
    super(code.getCode(), message, params);
  }

  public ListItemRuntimeException(ListItemRuntimeException.Error code, Map<String, ?> paramMap) {
    super(code.getCode(), code.getMessage(), (AppErrorMap) null, paramMap, null);
  }

  public enum Error {

    INVALID_DTO_IN(ErrorCode.application(ERROR_PREFIX + "/invalidDtoIn"), "DtoIn is not valid."),
    ITEM_DAO_LIST_ITEMS_FAILED(ErrorCode.application(ERROR_PREFIX + "/itemDaoListItemsFailed"), "List items by item DAO failed.");

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
