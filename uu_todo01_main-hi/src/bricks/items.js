//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Lsi from "../bricks/items-lsi";
import Calls from "../calls";
//@@viewOff:imports

export const Items = UU5.Common.VisualComponent.create({
  //@@viewOn:mixins
  mixins: [UU5.Common.BaseMixin],
  //@@viewOff:mixins

  //@@viewOn:statics
  statics: {
    tagName: Config.TAG + "Items",
    classNames: {
      main: (props, state) => Config.Css.css``
    },
    lsi: { ...Lsi }
  },
  //@@viewOff:statics

  //@@viewOn:propTypes
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  getDefaultProps() {
    return {
      list: null
    }
  },
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  componentDidUpdate() {
    this._listDataManager.reload();
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _loadItems() {
    return Calls.listItem(this.props.list);
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Common.ListDataManager
        onReload={this._loadItems}
        ref_={(me) => {this._listDataManager = me}}
      >
        {( {data, errorState} ) => {
          if (data == null) {
            return (
              <UU5.Bricks.Alert
                content={this.getLsiComponent("selectList")}
                offsetTop={50}
                block={true} />
            );
          }
          else if (errorState) {
            return (
              <UU5.Bricks.Alert
                content={this.getLsiComponent("errorState")}
                offsetTop={50}
                block={true} />
            );
          } else if (data) {
            return (
              <>
                {data.map(
                  ({ id, text }) => (
                    <div key={id}>
                      <UU5.Bricks.Well style={ { borderBottom: '1px solid grey' } }>
                        <UU5.Bricks.P>
                          {text}
                        </UU5.Bricks.P>
                      </UU5.Bricks.Well>
                    </div>
                  )
                )}
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

export default Items;
