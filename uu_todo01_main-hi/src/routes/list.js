//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
import Items from "../bricks/items";
import Lists from "../bricks/list"
//@@viewOff:imports

export const List = UU5.Common.VisualComponent.create({
  //@@viewOn:mixins
  mixins: [UU5.Common.BaseMixin, UU5.Common.RouteMixin],
  //@@viewOff:mixins

  //@@viewOn:statics
  statics: {
    tagName: Config.TAG + "List",
    classNames: {
      main: (props, state) => Config.Css.css``
    }
  },
  //@@viewOff:statics

  //@@viewOn:propTypes
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (<UU5.Bricks.Container {...this.getMainPropsToPass()}
                                  noSpacing={true}>
      <UU5.Bricks.Row noSpacing={true} display={"flex"}>
        <UU5.Bricks.Column noSpacing={true} colWidth={"xs-12 s-3 m-3"}>
          <Lists />
        </UU5.Bricks.Column>
        <UU5.Bricks.Column noSpacing={false} colWidth={"xs-12 s-9 m-9"} style="backgroundColor: darblue;">
          <Items />
        </UU5.Bricks.Column>
      </UU5.Bricks.Row>
    </UU5.Bricks.Container>);
  }
  //@@viewOff:render
});

export default List;
