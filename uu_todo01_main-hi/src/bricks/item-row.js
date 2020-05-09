//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
//@@viewOff:imports

export const ItemRow = UU5.Common.VisualComponent.create({
  //@@viewOn:mixins
  mixins: [UU5.Common.BaseMixin],
  //@@viewOff:mixins

  //@@viewOn:statics
  statics: {
    tagName: Config.TAG + "ItemRow",
    classNames: {
      main: (props, state) => Config.Css.css``
    }
  },
  //@@viewOff:statics

  //@@viewOn:propTypes
  propTypes: {
    completed: UU5.PropTypes.bool,
    item: UU5.PropTypes.string,
    value: UU5.PropTypes.string,
    parentReloadFunc: UU5.PropTypes.func
  },
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  getDefaultProps() {
    return {
      completed: false,
      item: null,
      value: null,
      parentReloadFunc: undefined
    }
  },
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  getInitialState() {
    return {
      editable: false,
      checkBoxRef: null
    }
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _callCompleteItem(completed) {
    return Calls.completeItem({item: this.props.item, completed: completed});
  },

  _registerCheckbox(ref) {
    this.setState({
      checkBoxRef: ref
    });
  },

  _onChangeHandler(opt) {
    let completed = opt.value;
    this.state.checkBoxRef.setValue(completed);
    this._callCompleteItem(completed).then((r) => {
      this.props.parentReloadFunc()
    });
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Bricks.Div {...this.getMainPropsToPass()}>
        <UU5.Bricks.Well bgStyle={"filled"} borderRadius={"5px"} style={{marginBottom: "10px", padding: "10px"}}>
          <UU5.Bricks.Container noSpacing={true}>
            <UU5.Bricks.Row noSpacing={true}>
              <UU5.Bricks.Column colWidth={"m-1 l-1 xl-1"} noSpacing={true} className="uu5-common-left">
                <UU5.Forms.Checkbox
                  ref_={this._registerCheckbox}
                  value={this.props.completed}
                  disabled={this.props.completed}
                  controlled={false}
                  labelPosition={"left"}
                  style={{margin: "0px"}}
                  onChange={this._onChangeHandler}
                />
              </UU5.Bricks.Column>
              <UU5.Bricks.Column colWidth={"m-10 l-10 xl-10"} noSpacing={true}>
                <UU5.Bricks.Text disabled={this.props.completed} style={{marginTop: "0.2em", textDecoration: this.props.completed ? "line-through" : "none"}}>
                  {this.props.value}
                </UU5.Bricks.Text>
              </UU5.Bricks.Column>
              <UU5.Bricks.Column colWidth={"m-1 l-1 xl-1"} className="uu5-common-right">
                <UU5.Bricks.Button disabled={this.props.completed}>
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

export default ItemRow;
