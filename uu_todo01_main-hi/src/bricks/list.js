//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
//@@viewOff:imports

export const List = UU5.Common.VisualComponent.create({
  //@@viewOn:mixins
  mixins: [UU5.Common.BaseMixin],
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
  _loadList() {
    return Calls.listList();
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Common.ListDataManager
        onLoad={this._loadList}
      >
        {({ data, errorState }) => {
          if (errorState) {
            return "Error";
          } else if (data) {
            return (
              <>
                {data.map(
                  ({ id, name }) => (
                    <UU5.Bricks.Well style={"border-bottom: 1px solid grey;"} key={id}>
                      <UU5.Bricks.P>
                        {name}
                      </UU5.Bricks.P>
                    </UU5.Bricks.Well>
                  )
                )
                }
              </>
            );
          } else {
            return <UU5.Bricks.Loading />
          }
        }}
      </UU5.Common.ListDataManager>
    );
  }
  //@@viewOff:render
});

export default List;
