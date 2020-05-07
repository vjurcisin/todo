//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
import CreateListForm from "./create-list-form";
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
  getDefaultProps() {
    return {
      onClick: () => {}
    }
  },
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
      <>
        <UU5.Common.ListDataManager
          onLoad={this._loadList}
          ref_={(me) => {this._listDataManager = me}}
        >
          {({ data, errorState }) => {
            if (errorState) {
              return "Error";
            } else if (data) {
              return (
                <>
                  {data.map(
                    ({ id, name }) => (
                      <div onClick={() => this.props.onClick({list: id})} key={id}>
                        <UU5.Bricks.Well bgStyle={"underline"}>
                          <UU5.Bricks.P>
                            {name}
                          </UU5.Bricks.P>
                        </UU5.Bricks.Well>
                      </div>
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

        <UU5.Bricks.Well bgStyle={"transparent"}>
          {console.log("bricks list " + this._listDataManager)}
          <CreateListForm dataList={this._listDataManager} />
        </UU5.Bricks.Well>
      </>
    );
  }
  //@@viewOff:render
});

export default List;
