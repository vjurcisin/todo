package uu.todo01.main.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import uu.app.authorization.DefaultAuthorizationResult;
import uu.app.client.AppClient;
import uu.app.client.AppClientFactory;
import uu.app.client.RemoteApplicationException;
import uu.app.uri.Uri;
import uu.todo01.main.api.dto.list.ListCreateDtoIn;
import uu.todo01.main.api.dto.list.ListCreateDtoOut;
import uu.todo01.main.api.exceptions.list.CreateListRuntimeException.Error;

public class CreateListTest extends Todo01MainAbstractTest {

  private static final String AWID = "00000000000000000000000000000001";
  private static final String UC_NAME = "list/create";
  private static final String CONTEXT_PATH = "/uu-todo01-main/" + AWID + "/" + UC_NAME;
  private static final String HOST = "http://127.0.0.1:";

  // inject port from env because random port is used
  @LocalServerPort
  private int randomServerPort;

  @Inject
  private AppClientFactory factory;

  @Before
  public void setup() {
    when(authorization.authorize(any(), any()))
      .thenReturn(new DefaultAuthorizationResult(true, Collections.emptySet()));
  }

  @Test
  public void testCreateList() {
    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;

    ListCreateDtoIn dtoIn = new ListCreateDtoIn()
      .setName("Nakup");

    final ListCreateDtoOut dtoOut = appClient.post(Uri.parse(uri), dtoIn, ListCreateDtoOut.class);

    assertThat(dtoOut.getUuAppErrorMap().getUuAppErrorMap()).isEmpty();
    assertThat(dtoOut.getList().getAwid()).isEqualTo(AWID);
    assertThat(dtoOut.getList().getName()).isEqualTo(dtoIn.getName());
  }

  @Test
  public void testCreateListA2() {
    AppClient appClient = factory.newAppClient();
    String uri = HOST + randomServerPort + CONTEXT_PATH;

    ListCreateDtoIn dtoIn = new ListCreateDtoIn()
      .setName(null);

    assertThatThrownBy(() -> appClient.post(Uri.parse(uri), dtoIn, ListCreateDtoOut.class))
      .isInstanceOf(RemoteApplicationException.class)
      .hasFieldOrPropertyWithValue("code", Error.INVALID_DTO_IN.getCode());
  }
}
