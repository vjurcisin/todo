package uu.todo01.main.abl;

import javax.inject.Inject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import uu.app.validation.Validator;
import uu.todo01.main.dao.ListDao;

@Component
public class ListsAbl {

  @Inject
  private Validator validator;

  @Inject
  private ListDao itemDao;

  @Inject
  private ModelMapper modelMapper;

  // TODO

}
