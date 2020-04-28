package uu.todo01.main.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.Sets;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.mock.mockito.SpyBean;
import uu.app.authorization.DefaultAuthorizationResult;
import uu.app.client.AppClient;
import uu.app.client.AppClientFactory;
import uu.app.client.RemoteApplicationException;
import uu.app.uri.Uri;
import uu.todo01.main.abl.ItemsAbl;
import uu.todo01.main.api.dto.ItemCreateDtoIn;
import uu.todo01.main.api.dto.ItemCreateDtoOut;
import uu.todo01.main.api.exceptions.CreateItemRuntimeException.Error;

public class CreateItemTest extends Todo01MainAbstractTest {

  private static final String AWID = "00000000000000000000000000000001";
  private static final String UC_NAME = "item/create";
  private static final String CONTEXT_PATH = "/uu-todo01-main/" + AWID + "/" + UC_NAME;
  private static final String HOST = "http://127.0.0.1:";

  // inject port from env because random port is used
  @LocalServerPort
  private int randomServerPort;

  @Inject
  private AppClientFactory factory;

  @SpyBean
  private ItemsAbl itemsAbl;

  private AppClient appClient;

  private String uri;

  @Before
  public void setup() {
    this.appClient = factory.newAppClient();
    this.uri = HOST + randomServerPort + CONTEXT_PATH;

    when(itemsAbl.listExists(any(), any())).thenReturn(true);
    when(authorization.authorize(any(), any()))
      .thenReturn(new DefaultAuthorizationResult(true, Sets.newHashSet("Authorities")));
  }

  @Test
  public void testCreateItem() {
    ItemCreateDtoIn dtoIn = new ItemCreateDtoIn()
      .setList("5ea51be66240aa0190aa3ca7a")
      .setText("Horcica");

    final ItemCreateDtoOut dtoOut = appClient.post(Uri.parse(uri), dtoIn, ItemCreateDtoOut.class);

    assertThat(dtoOut.getUuAppErrorMap().getUuAppErrorMap()).isEmpty();
    assertThat(dtoOut.getItem().getAwid()).isEqualTo(AWID);
    assertThat(dtoOut.getItem().getList()).isEqualTo(dtoIn.getList());
    assertThat(dtoOut.getItem().getText()).isEqualTo(dtoIn.getText());
    assertThat(dtoOut.getItem().getCompleted()).isFalse();
  }

  @Test
  public void testCreateItemListNullA2() {
    ItemCreateDtoIn dtoIn = new ItemCreateDtoIn()
      .setList(null)
      .setText("Horcica");

    assertThatThrownBy(() -> appClient.post(Uri.parse(uri), dtoIn, ItemCreateDtoOut.class))
      .isInstanceOf(RemoteApplicationException.class)
      .hasFieldOrPropertyWithValue("code", Error.INVALID_DTO_IN.getCode());
  }

  @Test
  public void testCreateItemTextNullA2() {
    ItemCreateDtoIn dtoIn = new ItemCreateDtoIn()
      .setList("5ea51be66240aa0190aa3ca7a")
      .setText(null);

    assertThatThrownBy(() -> appClient.post(Uri.parse(uri), dtoIn, ItemCreateDtoOut.class))
      .isInstanceOf(RemoteApplicationException.class)
      .hasFieldOrPropertyWithValue("code", Error.INVALID_DTO_IN.getCode());
  }

  @Test
  public void testCreateItemA3() {
    when(itemsAbl.listExists(any(), any())).thenReturn(false);

    ItemCreateDtoIn dtoIn = new ItemCreateDtoIn()
      .setList("non-existing")
      .setText("Horcica");

    assertThatThrownBy(() -> appClient.post(Uri.parse(uri), dtoIn, ItemCreateDtoOut.class))
      .isInstanceOf(RemoteApplicationException.class)
      .hasFieldOrPropertyWithValue("code", Error.LIST_DOES_NOT_EXIST.getCode());
  }

}
