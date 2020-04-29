package uu.todo01.main.abl;

import javax.inject.Inject;
import org.springframework.stereotype.Component;
import uu.app.validation.ValidationResult;
import uu.app.validation.Validator;
import uu.app.validation.utils.ValidationResultUtils;
import uu.app.workspace.Profile;
import uu.app.workspace.dto.profile.SysSetProfileDtoIn;
import uu.todo01.main.api.dto.Todo01MainInitDtoIn;
import uu.todo01.main.api.dto.Todo01MainInitDtoOut;
import uu.todo01.main.api.exceptions.Todo01MainInitRuntimeException;
import uu.todo01.main.api.exceptions.Todo01MainInitRuntimeException.Error;
import uu.todo01.main.dao.Todo01MainDao;

@Component
public final class Todo01MainAbl {

  private static final String AUTHORITIES_CODE = "Authorities";

  @Inject
  private Validator validator;

  @Inject
  private Profile profile;

  @Inject
  private Todo01MainDao todo01MainDao;

  public Todo01MainInitDtoOut init(String awid, Todo01MainInitDtoIn dtoIn) {
    // HDS 1
    ValidationResult validationResult = validator.validate(dtoIn);
    if (!validationResult.isValid()) {
      // A1
      throw new Todo01MainInitRuntimeException(Error.INVALID_DTO_IN, ValidationResultUtils.validationResultToAppErrorMap(validationResult));
    }

    // HDS 2
    SysSetProfileDtoIn setProfileDtoIn = new SysSetProfileDtoIn();
    setProfileDtoIn.setCode(AUTHORITIES_CODE);
    setProfileDtoIn.setRoleUri(dtoIn.getAuthoritiesUri());
    profile.setProfile(awid, setProfileDtoIn);

    // HDS 3 - HDS N
    // TODO Implement according to application needs...

    // HDS N+1
    return new Todo01MainInitDtoOut();
  }

}
