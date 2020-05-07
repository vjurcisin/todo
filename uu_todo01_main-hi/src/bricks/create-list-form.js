//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
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
  propTypes: {
    onSave: UU5.PropTypes.func,
    onCancel: UU5.PropTypes.func
  },
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  getDefaultProps() {
    return {
      onSave: () => {},
      onCancel: undefined
    };
  },
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  getInitialState() {
    return {
      formRef: null
    };
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _registerForm(form) {
    this.setState({
      formRef: form
    });
  },

  _saveForm(opt) {
    // save values to DB
    this.props.onSave(opt.values);
  },

  _renderForm() {
    return (
      <UU5.Forms.Text name="name" label={<UU5.Bricks.Lsi lsi={{ en: "Name", cs: "Nazov" }} />} required />
    );
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <>
        <UU5.Forms.ContextForm
          ref_={this._registerForm}
          onSave={this._saveForm}
          onCancel={this.props.onCancel}
          labelColWidth="xs-12 m-5"
          inputColWidth="xs-12 m-7"
        >
          {this.state.formRef && this._renderForm()}
        </UU5.Forms.ContextForm>
      </>
    );
  }
  //@@viewOff:render
});

export default CreateListForm;
