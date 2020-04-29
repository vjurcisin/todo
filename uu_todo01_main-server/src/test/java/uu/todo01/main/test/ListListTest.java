package uu.todo01.main.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import com.google.common.collect.Sets;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.mock.mockito.MockBean;
import uu.app.authorization.DefaultAuthorizationResult;
import uu.app.client.AppClient;
import uu.app.client.AppClientFactory;
import uu.todo01.main.dao.ListDao;

@Ignore
public class ListListTest extends Todo01MainAbstractTest {

  private static final String AWID = "00000000000000000000000000000001";
  private static final String UC_NAME = "list/list";
  private static final String CONTEXT_PATH = "/uu-todo01-main/" + AWID + "/" + UC_NAME;
  private static final String HOST = "http://127.0.0.1:";

  // inject port from env because random port is used
  @LocalServerPort
  private int randomServerPort;

  @Inject
  private AppClientFactory factory;

  @MockBean
  private ListDao listDao;

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
  public void testListList() {
    // TODO
  }

}
