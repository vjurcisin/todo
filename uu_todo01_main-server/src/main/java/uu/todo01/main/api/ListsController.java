package uu.todo01.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.todo01.main.abl.ListsAbl;
import uu.todo01.main.api.dto.list.ListCreateDtoIn;
import uu.todo01.main.api.dto.list.ListCreateDtoOut;
import uu.todo01.main.api.dto.list.ListDeleteDtoIn;
import uu.todo01.main.api.dto.list.ListDeleteDtoOut;
import uu.todo01.main.api.dto.list.ListGetDtoIn;
import uu.todo01.main.api.dto.list.ListGetDtoOut;
import uu.todo01.main.api.dto.list.ListListDtoIn;
import uu.todo01.main.api.dto.list.ListListDtoOut;
import uu.todo01.main.api.dto.list.ListUpdateDtoIn;
import uu.todo01.main.api.dto.list.ListUpdateDtoOut;

@CommandController
public final class ListsController {

  @Inject
  private ListsAbl listsAbl;

  @Command(path = "list/create", method = POST)
  public ListCreateDtoOut createList(CommandContext<ListCreateDtoIn> ctx) {
    final ListCreateDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return listsAbl.createList(awid, dtoIn);
  }

  @Command(path = "list/get", method = GET)
  public ListGetDtoOut getList(CommandContext<ListGetDtoIn> ctx) {
    final ListGetDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return listsAbl.getList(awid, dtoIn);
  }

  @Command(path = "list/update", method = POST)
  public ListUpdateDtoOut updateList(CommandContext<ListUpdateDtoIn> ctx) {
    final ListUpdateDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return listsAbl.updateList(awid, dtoIn);
  }

  @Command(path = "list/list", method = GET)
  public ListListDtoOut listList(CommandContext<ListListDtoIn> ctx) {
    final ListListDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return listsAbl.listList(awid, dtoIn);
  }

  @Command(path = "list/delete", method = POST)
  public ListDeleteDtoOut deleteList(CommandContext<ListDeleteDtoIn> ctx) {
    final ListDeleteDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return listsAbl.deleteList(awid, dtoIn);
  }

}
