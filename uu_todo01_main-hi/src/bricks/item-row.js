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

  _onClickItemRow(value) {
    this.setState({
      editable: value
    });
  },

  _callUpdateItem() {
    if (this._editItemText.getValue().trim() === "") {
      this._editItemText.setError("Toto pole je povinné.")
    } else {
      Calls.updateItem({item: this.props.item, text: this._editItemText.getValue()})
        .then(() => {
          this.props.parentReloadFunc();
        });
    }
  },

  _callDeleteItem() {
    Calls.deleteItem({id: this.props.item})
      .then(() => {
        this.props.parentReloadFunc()
      });
  },

  _renderEditable() {
    return (
      <>
        <UU5.Bricks.Column colWidth={"m-10 l-10 xl-10"} noSpacing={true}>
          <UU5.Forms.Text
            ref_={(me) => {this._editItemText = me}}
            value={this.props.value}
            style={{margin: "0"}}
            onEnter={this._callUpdateItem}
            required={true}
          />
        </UU5.Bricks.Column>
        <UU5.Bricks.Column colWidth={"m-2 l-2 xl-2"} noSpacing={true} className="uu5-common-right">
          <UU5.Bricks.ButtonGroup>
            {/*Delete*/}
            <UU5.Bricks.Button
              style={{marginRight: "0.5em"}}
              bgStyle={"transparent"}
              onClick={this._callDeleteItem}
            >
              <UU5.Bricks.Icon icon={"mdi-delete"} />
            </UU5.Bricks.Button>
            <UU5.Bricks.Button
              style={{marginRight: "0.5em"}}
              onClick={() => {this._onClickItemRow(false)}}
            >
              <UU5.Bricks.Icon icon={"uu5-cross"} />
            </UU5.Bricks.Button>
            {/*OK*/}
            <UU5.Bricks.Button
              onClick={this._callUpdateItem}
            >
              <UU5.Bricks.Icon icon={"uu5-ok"} />
            </UU5.Bricks.Button>
          </UU5.Bricks.ButtonGroup>
        </UU5.Bricks.Column>
      </>
    );
  },

  _renderReadonly() {
    return (
      <>
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
          <UU5.Bricks.Button
            onClick={() => {this._onClickItemRow(true)}}
          >
            <UU5.Bricks.Icon icon={"mdi-pencil"} />
          </UU5.Bricks.Button>
        </UU5.Bricks.Column>
      </>
    );
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Bricks.Div {...this.getMainPropsToPass()}>
        <UU5.Bricks.Well bgStyle={"filled"} borderRadius={"5px"} style={{marginBottom: "10px", padding: "10px"}}>
          <UU5.Bricks.Container noSpacing={true}>
            <UU5.Bricks.Row noSpacing={true}>
              {this.state.editable ? this._renderEditable() : this._renderReadonly()}
            </UU5.Bricks.Row>
          </UU5.Bricks.Container>
        </UU5.Bricks.Well>
      </UU5.Bricks.Div>
    );
  }
  //@@viewOff:render
});

export default ItemRow;
