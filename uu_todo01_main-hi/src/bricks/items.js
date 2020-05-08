//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Lsi from "../bricks/items-lsi";
import Calls from "../calls";
import ItemRow from "./item-row";
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
  getInitialState() {
    return {
      textInputRef: null,
    };
  },

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

  _createItem(dtoIn) {
    if (dtoIn.text.trim() === "") {
      alert("Missing text");
    } else {
      Calls.createItem(dtoIn);
      this.componentDidUpdate();
    }
  },

  _registerTextInput(textInput) {
    this.setState({
      textInputRef: textInput
    });
  },

  _renderAddItemForm() {
    return (
        <UU5.Bricks.Container noSpacing={true}>
          <UU5.Bricks.Row noSpacing={true}>
            <UU5.Bricks.Column colWidth={"m-11 l-11 xl-11"} noSpacing={true}>
                <UU5.Forms.Text
                  ref_={this._registerTextInput}
                  name="name"
                  required={false}
                  placeholder={"Add a todo..."}
                />
            </UU5.Bricks.Column>
            <UU5.Bricks.Column colWidth={"m-1 l-1 xl-1"} className="uu5-common-right">
                <UU5.Bricks.Button onClick={() => this._createItem({list: this.props.list.list, text: this.state.textInputRef.getValue()})}>
                  <UU5.Bricks.Icon icon={"uu5-ok"} />
                </UU5.Bricks.Button>
            </UU5.Bricks.Column>
            </UU5.Bricks.Row>
          </UU5.Bricks.Container>
    );
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <div style={{padding: "10px 10px 10px 10px", backgroundColor: "#2196F3"}}>
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
                  {this._renderAddItemForm()}
                  {data.map(
                    ({ id, text }) => (
                      <ItemRow item={id} value={text} key={Math.random()} />
                    )
                  )}
                </>
              );
            } else {
              return <UU5.Bricks.Loading />
            }
          }}
        </UU5.Common.ListDataManager>
      </div>
    );
  }
  //@@viewOff:render
});

export default Items;
