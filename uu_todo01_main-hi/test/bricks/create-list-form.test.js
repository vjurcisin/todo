import UU5 from "uu5g04";
import UuTodo01 from "uu_todo01_main-hi";

const { shallow } = UU5.Test.Tools;

describe(`UuTodo01.Bricks.CreateListForm`, () => {
  it(`default props`, () => {
    const wrapper = shallow(<UuTodo01.Bricks.CreateListForm />);
    expect(wrapper).toMatchSnapshot();
  });
});
