/* eslint-disable */

// Item validations

const itemCreateDtoInType = shape({
  list: string(1, 32).isRequired(),
  text: string(1, 1000).isRequired()
});

const itemGetDtoInType = shape({
  id: string(1, 32).isRequired()
})

const itemUpdateDtoInType = shape({
  item: id().isRequired(),
  list: id(),
  text: string(1, 1000)
})

const itemCompleteDtoInType = shape({
  item: id().isRequired(),
  completed: boolean()
})

const itemListDtoInType = shape({
  list: id(),
  completed: boolean(),
  pageInfo: shape({
    pageIndex: integer(0, null),
    pageSize: integer(0, null)
  })
})

// List validations

const listCreateDtoInType = shape({
  name: string(1, 30).isRequired()
})

const listGetDtoInType = shape({
  id: id().isRequired()
})

const listListDtoInType = shape({
  pageInfo: shape({
    pageIndex: integer(0, null),
    pageSize: integer(0, null)
  })
})
