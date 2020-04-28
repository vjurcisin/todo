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
import org.springframework.boot.test.mock.mockito.MockBean;
import uu.app.authorization.DefaultAuthorizationResult;
import uu.app.client.AppClient;
import uu.app.client.AppClientFactory;
import uu.app.client.RemoteApplicationException;
import uu.app.uri.Uri;
import uu.todo01.main.abl.entity.Item;
import uu.todo01.main.api.dto.ItemGetDtoIn;
import uu.todo01.main.api.dto.ItemGetDtoOut;
import uu.todo01.main.api.exceptions.GetItemRuntimeException.Error;
import uu.todo01.main.dao.ItemDao;

public class GetItemTest extends Todo01MainAbstractTest {

  private static final String AWID = "00000000000000000000000000000001";
  private static final String UC_NAME = "item/get";
  private static final String CONTEXT_PATH = "/uu-todo01-main/" + AWID + "/" + UC_NAME;
  private static final String HOST = "http://127.0.0.1:";

  // inject port from env because random port is used
  @LocalServerPort
  private int randomServerPort;

  @Inject
  private AppClientFactory factory;

  @MockBean
  private ItemDao itemDao;

  private AppClient appClient;

  private String uri;

  @Before
  public void setup() {
    this.appClient = factory.newAppClient();
    this.uri = HOST + randomServerPort + CONTEXT_PATH;

    when(authorization.authorize(any(), any()))
      .thenReturn(new DefaultAuthorizationResult(true, Sets.newHashSet("Authorities")));
  }

  @Test
  public void testGetItem() {
    Item mockItem = new Item()
      .setCompleted(true)
      .setList("1234567")
      .setText("Horcia");
    when(itemDao.get(any(), any())).thenReturn(mockItem);

    ItemGetDtoIn dtoIn = new ItemGetDtoIn()
      .setId("1234567890");

    final ItemGetDtoOut dtoOut = appClient.get(Uri.parse(uri), dtoIn, ItemGetDtoOut.class);

    assertThat(dtoOut.getItem().getList()).isEqualTo(mockItem.getList());
    assertThat(dtoOut.getItem().getText()).isEqualTo(mockItem.getText());
    assertThat(dtoOut.getItem().getCompleted()).isEqualTo(mockItem.getCompleted());
  }

  @Test
  public void testGetItemA3() {
    when(itemDao.get(any(), any())).thenReturn(null);

    ItemGetDtoIn dtoIn = new ItemGetDtoIn()
      .setId("1234567890");

    assertThatThrownBy(() -> appClient.get(Uri.parse(uri), dtoIn, ItemGetDtoOut.class))
      .isInstanceOf(RemoteApplicationException.class)
      .hasFieldOrPropertyWithValue("code", Error.ITEM_DOES_NOT_EXIST.getCode());
  }
}
