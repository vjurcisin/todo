/* eslint-disable */

const itemCreateDtoInType = shape({
  list: string(1,32).isRequired(),
  text: string(1,1000).isRequired()
});

const listCreateDtoInType = shape({
  name: string(1,30).isRequired()
})
