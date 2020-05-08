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
  propTypes: {
    textInputRef: UU5.PropTypes.object,
    unCompletelistDataManagerRef: UU5.PropTypes.object,
    completedListDataManagerRef: UU5.PropTypes.object
  },
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
      unCompletelistDataManagerRef: null,
      completedListDataManagerRef: null,
      showCompleted: false
    };
  },

  componentDidUpdate() {
    this.state.unCompletelistDataManagerRef.reload();
    this.state.completedListDataManagerRef.reload();
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _loadUncompletedItems() {
    return Calls.listItem({list: this.props.list, completed: false} );
  },

  _loadCompletedItems() {
    return Calls.listItem({list: this.props.list, completed: true} );
  },

  async _createItem(dtoIn) {
    if (dtoIn.text === "") {
      alert("Missing text");
    } else {
      await Calls.createItem(dtoIn);
      this.state.unCompletelistDataManagerRef.reload();
    }
  },

  _registerTextInput(ref) {
    this.setState({
      textInputRef: ref
    });
  },

  _registerUncompletedListDataManager(ref) {
    this.setState({
      unCompletelistDataManagerRef: ref
    });
  },

  _registerCompletedListDataManager(ref) {
    this.setState({
      completedListDataManagerRef: ref
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
                <UU5.Bricks.Button
                  onClick={() => this._createItem({list: this.props.list, text: this.state.textInputRef.getValue()})}
                  style={{marginTop: "1.7em"}}
                >
                  <UU5.Bricks.Icon icon={"uu5-ok"} />
                </UU5.Bricks.Button>
            </UU5.Bricks.Column>
            </UU5.Bricks.Row>
          </UU5.Bricks.Container>
    );
  },

  _renderItems(onReloadFunc, registerListFunc, completed) {
    return (
      <UU5.Common.ListDataManager
        onLoad={onReloadFunc}
        ref_={registerListFunc}
      >
        {( {data} ) => {
            return (
              <>
                {data && data.map(
                  ({ id, text }) => (
                    <ItemRow item={id} value={text} completed={completed} key={Math.random()} />
                  )
                )}
              </>
            );
        }}
      </UU5.Common.ListDataManager>
    );
  },

  _toggleShowCompleted() {
    this.setState((prevState, props) => {
      return {showCompleted: !prevState.showCompleted}
    });
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <div style={{padding: "10px 10px 10px 10px", backgroundColor: "#2196F3"}}>
        {this._renderAddItemForm()}
        {this._renderItems(this._loadUncompletedItems, this._registerUncompletedListDataManager, false)}

        <UU5.Bricks.Button
          onClick={this._toggleShowCompleted}
          style={{marginBottom: "2em", marginTop: "1em"}}>
          {this.state.showCompleted ? "Show completed" : "Hide completed"}
        </UU5.Bricks.Button>

        <UU5.Bricks.Div hidden={this.state.showCompleted}>
        {this._renderItems(this._loadCompletedItems, this._registerCompletedListDataManager, true)}
        </UU5.Bricks.Div>
      </div>
    );
  }
  //@@viewOff:render
});

export default Items;
