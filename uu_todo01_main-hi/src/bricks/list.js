//@@viewOn:imports
import * as UU5 from "uu5g04";
import "uu5g04-bricks";
import Config from "./config/config.js";
import Calls from "../calls";
import Lsi from "../bricks/list-lsi";
import CreateLsi from "../bricks/create-list-lsi.js";
import CreateListForm from "./create-list-form";
import ListRow from "./list-row";
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
    },
    lsi: { ...CreateLsi, ...Lsi }
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
  getInitialState() {
    return {
      selectedList: null
    };
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _loadList() {
    return Calls.listList();
  },

  _createList(dtoIn) {
    return Calls.createList(dtoIn);
  },

  _registerModal(modal) {
    this._modal = modal;
  },

  _openModal() {
    this._modal.open();
  },

  _cancelForm() {
    this._modal.close();
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <>
        <UU5.Common.ListDataManager
          onLoad={this._loadList}
          onCreate={this._createList}
        >
          {({ data, handleCreate }) => {
              return (
                <>
                  <UU5.Forms.ContextModal
                    ref_={this._registerModal}
                    header={this.getLsiComponent("formHeader")}
                    footer={<UU5.Forms.ContextControls />}
                  >
                    <CreateListForm onSave={values => handleCreate(values)} onCancel={this._cancelForm} />
                  </UU5.Forms.ContextModal>

                  {data && data.map(
                    ({ id, name }) => (
                      <ListRow value={name} key={Math.random()} idValue={id} onClick={() => this.props.onClick({list: id})} />
                    )
                  )}
                </>
              );
          }}
        </UU5.Common.ListDataManager>
        <UU5.Bricks.Well bgStyle={"transparent"}>
          <UU5.Bricks.Link onClick={this._openModal}>
            <UU5.Bricks.Lsi lsi={{ en: "+ Create list", cs: "+ Vytvorit list" }} />
          </UU5.Bricks.Link>
        </UU5.Bricks.Well>
      </>
    );
  }
  //@@viewOff:render
});

export default List;
