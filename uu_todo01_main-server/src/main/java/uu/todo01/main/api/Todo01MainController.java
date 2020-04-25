package uu.todo01.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.todo01.main.abl.Todo01MainAbl;
import uu.todo01.main.api.dto.Todo01MainInitDtoIn;
import uu.todo01.main.api.dto.Todo01MainInitDtoOut;

@CommandController
public final class Todo01MainController {

  @Inject
  private Todo01MainAbl todo01MainAbl;

  @Command(path = "init", method = POST)
  public Todo01MainInitDtoOut create(CommandContext<Todo01MainInitDtoIn> ctx) {
    Todo01MainInitDtoOut dtoOut = todo01MainAbl.init(ctx.getUri().getAwid(), ctx.getDtoIn());
    return dtoOut;
  }

}
