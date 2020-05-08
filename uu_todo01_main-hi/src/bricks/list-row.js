//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
//@@viewOff:imports

export const ListRow = UU5.Common.VisualComponent.create({
  //@@viewOn:mixins
  mixins: [UU5.Common.BaseMixin],
  //@@viewOff:mixins

  //@@viewOn:statics
  statics: {
    tagName: Config.TAG + "ListRow",
    classNames: {
      main: (props, state) => Config.Css.css``
    }
  },
  //@@viewOff:statics

  //@@viewOn:propTypes
  propTypes: {
    value: UU5.PropTypes.string
  },
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  getDefaultProps() {
    return {
      value: null
    }
  },
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  getInitialState() {
    return { active: false }
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _handleOnClick() {
    this.setState({ active: true });
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Bricks.Div {...this.getMainPropsToPass()} onClick={this._handleOnClick} mainAttrs={{ onClick: this._handleOnClick }}>
          <UU5.Bricks.Well bgStyle={"underline"}>
            <UU5.Bricks.Container noSpacing={true}>
              <UU5.Bricks.Row>
                <UU5.Bricks.Column width="80%">
                  {this.props.value}
                </UU5.Bricks.Column>
                <UU5.Bricks.Column width="20%">
                  <UU5.Bricks.Button>
                    <UU5.Bricks.Icon icon={"mdi-pencil"} />
                  </UU5.Bricks.Button>
                </UU5.Bricks.Column>
              </UU5.Bricks.Row>
            </UU5.Bricks.Container>
          </UU5.Bricks.Well>
      </UU5.Bricks.Div>
    );
  }
  //@@viewOff:render
});

export default ListRow;
