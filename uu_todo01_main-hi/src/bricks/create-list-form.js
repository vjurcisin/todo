//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
//@@viewOff:imports

export const CreateListForm = UU5.Common.VisualComponent.create({
  //@@viewOn:mixins
  mixins: [UU5.Common.BaseMixin],
  //@@viewOff:mixins

  //@@viewOn:statics
  statics: {
    tagName: Config.TAG + "CreateListForm",
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
      dataList: null
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
  _createList(dtoIn) {
    return Calls.createList(dtoIn);
  },

  _getHeader() {
    return (
      <UU5.Forms.ContextHeader
        content={<UU5.Bricks.Lsi lsi={{ en: "Create a new list", cs: "Vytvorit novy list" }} />}
      />
    )
  },

  _getForm(modal) {
    return (
      <UU5.Forms.ContextForm
        onSave={opt => {
          this._createList(opt.values)
          this.props.dataList.reload();
          modal && modal.close();
        }}
        onCancel={() => {
          modal && modal.close();
        }}
      >
        <UU5.Forms.Text name="name" label={<UU5.Bricks.Lsi lsi={{ en: "Name", cs: "Nazov" }} />} required />
      </UU5.Forms.ContextForm>
    )
  },

  _getControls() {
    return (
      <UU5.Forms.ContextControls
        buttonSubmitProps={{ content: <UU5.Bricks.Lsi lsi={{ en: "Create", cs: "Vytvorit" }} /> }}
        buttonCancelProps={{ content: <UU5.Bricks.Lsi lsi={{ en: "Cancel", cs: "Zavriet" }} /> }}
      />
    )
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <UU5.Bricks.Div {...this.getMainPropsToPass()}>
        <UU5.Forms.ContextModal ref_={modal => this._modal = modal} />

        <UU5.Bricks.Link
          content={<UU5.Bricks.Lsi lsi={{ en: "+ Create list", cs: "+ Vytvorit list" }} />}
          onClick={() => this._modal.open({
            header: this._getHeader(),
            content: this._getForm(this._modal),
            footer: this._getControls()
          })}
        />
      </UU5.Bricks.Div>);
  }
  //@@viewOff:render
});

export default CreateListForm;
