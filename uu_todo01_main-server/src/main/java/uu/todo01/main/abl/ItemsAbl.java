package uu.todo01.main.abl;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.validation.Validator;
import uu.todo01.main.dao.ItemDao;

@Component
public class ItemsAbl {

  @Inject
  private Validator validator;

  @Inject
  private ItemDao itemDao;

  @Inject
  private ModelMapper modelMapper;

   // TODO

}
