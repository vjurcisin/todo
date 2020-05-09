//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
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
    list: UU5.PropTypes.string,
    value: UU5.PropTypes.string,
    parentReloadFunc: UU5.PropTypes.func
  },
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  getDefaultProps() {
    return {
      list: null,
      value: null,
      parentReloadFunc: undefined
    }
  },
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  getInitialState() {
    return {
      active: false,
      editable: false,
    }
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _handleOnClick() {
    this.setState((prevState, props) => {
      return ({
        active: !prevState.active
      })});
  },

  _registerEdit() {
    this.setState({
      editable: true,
    });
  },

  _renderReadonly() {
    return (
      <>
        <UU5.Bricks.Column width="80%">
          {this.props.value}
        </UU5.Bricks.Column>
        <UU5.Bricks.Column width="20%">
          { this.state.active &&
          <UU5.Bricks.Button
            onClick={this._registerEdit}
          >
            <UU5.Bricks.Icon icon={"mdi-pencil"} />
          </UU5.Bricks.Button>
          }
        </UU5.Bricks.Column>
      </>
    );
  },

  _callUpdateList() {
    if (this._editItemText.getValue().trim() === "") {
      this._editItemText.setError("Toto pole je povinné.")
    } else {
      Calls.updateList({list: this.props.list, name: this._editItemText.getValue()})
        .then(() => {
          this.props.parentReloadFunc();
        });
    }
  },

  _callDeleteList() {
    this.setState({
      editable: false
    })
  },

  _renderEditable() {
    return (
      <>
        <UU5.Bricks.Column colWidth={"m-10 l-10 xl-10"} noSpacing={true}>
          <UU5.Forms.Text
            ref_={(me) => {this._editItemText = me}}
            value={this.props.value}
            style={{margin: "0"}}
            onEnter={this._callUpdateList}
            required={true}
          />
        </UU5.Bricks.Column>
        <UU5.Bricks.Column colWidth={"m-2 l-2 xl-2"} noSpacing={true} className="uu5-common-right">
          <UU5.Bricks.ButtonGroup>
            {/*Delete*/}
            <UU5.Bricks.Button
              style={{marginRight: "0.5em"}}
              bgStyle={"transparent"}
              onClick={this._callDeleteList}
            >
              <UU5.Bricks.Icon icon={"mdi-delete"} />
            </UU5.Bricks.Button>
            {/*OK*/}
            <UU5.Bricks.Button
              onClick={this._callUpdateList}
            >
              <UU5.Bricks.Icon icon={"uu5-ok"} />
            </UU5.Bricks.Button>
          </UU5.Bricks.ButtonGroup>
        </UU5.Bricks.Column>
      </>
    );
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Bricks.Div {...this.getMainPropsToPass()}
                      mainAttrs={{ onClick: this._handleOnClick }}
                      style={{ backgroundColor: this.state.active ? "#90CAF9" : ""}}
      >
          <UU5.Bricks.Well bgStyle={"underline"}>
            <UU5.Bricks.Container noSpacing={true}>
              <UU5.Bricks.Row>
                {this.state.editable ? this._renderEditable() : this._renderReadonly()}
              </UU5.Bricks.Row>
            </UU5.Bricks.Container>
          </UU5.Bricks.Well>
      </UU5.Bricks.Div>
    );
  }
  //@@viewOff:render
});

export default ListRow;
