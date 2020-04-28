package uu.todo01.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMethod;
import uu.app.authentication.Session;
import uu.app.authorization.AuthorizationResult;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.todo01.main.abl.ItemsAbl;
import uu.todo01.main.api.dto.ItemCompleteDtoIn;
import uu.todo01.main.api.dto.ItemCompleteDtoOut;
import uu.todo01.main.api.dto.ItemCreateDtoIn;
import uu.todo01.main.api.dto.ItemCreateDtoOut;
import uu.todo01.main.api.dto.ItemGetDtoIn;
import uu.todo01.main.api.dto.ItemGetDtoOut;
import uu.todo01.main.api.dto.ItemListDtoIn;
import uu.todo01.main.api.dto.ItemListDtoOut;

@CommandController
public final class ItemsController {

  @Inject
  private ItemsAbl itemsAbl;

  @Command(path = "item/create", method = POST)
  public ItemCreateDtoOut createItem(CommandContext<ItemCreateDtoIn> ctx) {
    final ItemCreateDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return itemsAbl.createItem(awid, dtoIn);
  }

  @Command(path = "item/get", method = GET)
  public ItemGetDtoOut getItem(CommandContext<ItemGetDtoIn> ctx) {
    final ItemGetDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return itemsAbl.getItem(awid, dtoIn);
  }

  @Command(path = "item/complete", method = POST)
  public ItemCompleteDtoOut completeItem(CommandContext<ItemCompleteDtoIn> ctx) {
    final ItemCompleteDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return itemsAbl.completeItem(awid, dtoIn);
  }

  @Command(path = "item/list", method = GET)
  public ItemListDtoOut listItem(CommandContext<ItemListDtoIn> ctx) {
    final ItemListDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return itemsAbl.listItem(awid, dtoIn);
  }
}
