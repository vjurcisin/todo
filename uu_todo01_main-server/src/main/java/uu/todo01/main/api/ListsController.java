package uu.todo01.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.todo01.main.abl.ItemsAbl;
import uu.todo01.main.abl.ListsAbl;
import uu.todo01.main.api.dto.ItemCreateDtoIn;
import uu.todo01.main.api.dto.ItemCreateDtoOut;
import uu.todo01.main.api.dto.ListCreateDtoIn;
import uu.todo01.main.api.dto.ListCreateDtoOut;

@CommandController
public final class ListsController {

  @Inject
  private ListsAbl listsAbl;

  @Command(path = "list/create", method = POST)
  public ListCreateDtoOut createItem(CommandContext<ListCreateDtoIn> ctx) {
    final ListCreateDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return listsAbl.createItem(awid, dtoIn);
  }
}
