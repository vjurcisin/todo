package uu.todo01.main.api.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import uu.app.validation.ValidationType;

@ValidationType("todo01MainInitDtoInType")
public final class Todo01MainInitDtoIn {

  private String authoritiesUri;

  public String getAuthoritiesUri() {
    return authoritiesUri;
   }

  public void setAuthoritiesUri(String authoritiesUri) {
    this.authoritiesUri = authoritiesUri;
  }

  @Override
  public String toString() {
    ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    try {
      return ow.writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return super.toString();
    }
  }

}
