package uu.todo01.main.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.inject.Inject;
import org.springframework.web.bind.annotation.RequestMethod;
import uu.app.authentication.Session;
import uu.app.authorization.AuthorizationResult;
import uu.app.server.CommandContext;
import uu.app.server.annotation.Command;
import uu.app.server.annotation.CommandController;
import uu.todo01.main.abl.ItemsAbl;
import uu.todo01.main.api.dto.ItemCreateDtoIn;
import uu.todo01.main.api.dto.ItemCreateDtoOut;

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
}
