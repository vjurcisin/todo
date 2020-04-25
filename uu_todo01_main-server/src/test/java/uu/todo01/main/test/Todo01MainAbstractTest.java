package uu.todo01.main.test;

import javax.inject.Inject;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uu.app.authorization.Authorization;
import uu.app.workspace.AppWorkspace;
import uu.todo01.main.SubAppRunner;
import uu.todo01.main.abl.Todo01MainAbl;

@RunWith(SpringRunner.class)
@PropertySource("classpath:test.properties")
@SpringBootTest(classes = {SubAppRunner.class, Todo01MainAbl.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class Todo01MainAbstractTest {

  @MockBean
  protected Authorization authorization;

  @Inject
  protected AppWorkspace workspaceModel;

  @Inject
  protected Todo01MainAbl todo01MainAbl;

}
