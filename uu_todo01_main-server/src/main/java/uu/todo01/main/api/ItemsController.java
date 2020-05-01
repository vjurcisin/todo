package uu.todo01.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.todo01.main.abl.ItemsAbl;
import uu.todo01.main.api.dto.item.ItemCompleteDtoIn;
import uu.todo01.main.api.dto.item.ItemCompleteDtoOut;
import uu.todo01.main.api.dto.item.ItemCreateDtoIn;
import uu.todo01.main.api.dto.item.ItemCreateDtoOut;
import uu.todo01.main.api.dto.item.ItemDeleteDtoIn;
import uu.todo01.main.api.dto.item.ItemDeleteDtoOut;
import uu.todo01.main.api.dto.item.ItemGetDtoIn;
import uu.todo01.main.api.dto.item.ItemGetDtoOut;
import uu.todo01.main.api.dto.item.ItemListDtoIn;
import uu.todo01.main.api.dto.item.ItemListDtoOut;
import uu.todo01.main.api.dto.item.ItemUpdateDtoIn;
import uu.todo01.main.api.dto.item.ItemUpdateDtoOut;

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

  @Command(path = "item/update", method = POST)
  public ItemUpdateDtoOut updateItem(CommandContext<ItemUpdateDtoIn> ctx) {
    final ItemUpdateDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return itemsAbl.updateItem(awid, dtoIn);
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

  @Command(path = "item/delete", method = POST)
  public ItemDeleteDtoOut deleteItem(CommandContext<ItemDeleteDtoIn> ctx) {
    final ItemDeleteDtoIn dtoIn = ctx.getDtoIn();
    String awid = ctx.getUri().getAwid();

    return itemsAbl.deleteItem(awid, dtoIn);
  }
}
