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
  propTypes: {
    listChanged: UU5.PropTypes.func,
  },
  //@@viewOff:propTypes

  //@@viewOn:getDefaultProps
  getDefaultProps() {
    return {
      listChanged: () => {}
    }
  },
  //@@viewOff:getDefaultProps

  //@@viewOn:reactLifeCycle
  getInitialState() {
    return {
      listDataManagerRef: null
    };
  },

  componentDidUpdate() {
    this.state.listDataManagerRef.reload();
  },
  //@@viewOff:reactLifeCycle

  //@@viewOn:interface
  reloadListaManagers() {
    this.state.listDataManagerRef.reload();
  },
  //@@viewOff:interface

  //@@viewOn:overriding
  //@@viewOff:overriding

  //@@viewOn:private
  _loadList() {
    return Calls.listList();
  },

  async _createList(dtoIn) {
    await Calls.createList(dtoIn);
    this._modal.close();
    this.state.listDataManagerRef.reload();
  },

  _registerListDataManager(ref) {
    this.setState({
      listDataManagerRef: ref
    });
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

  _handleOnClickListChanged(id) {
    this.props.listChanged(id)
  },
  //@@viewOff:private

  //@@viewOn:render
  render() {
    return (
      <>
        <UU5.Common.ListDataManager
          onLoad={this._loadList}
          ref_={this._registerListDataManager}
        >
          {({ data }) => {
              return (
                <>
                  {data && data.map(
                    ({ id, name }) => (
                      <div onClick={() => this._handleOnClickListChanged(id)} key={Math.random()}>
                        <ListRow
                          list={id}
                          value={name}
                          parentReloadFunc={this.reloadListaManagers}
                        />
                      </div>
                    )
                  )}
                </>
              );
          }}
        </UU5.Common.ListDataManager>
        <UU5.Forms.ContextModal
          ref_={this._registerModal}
          header={this.getLsiComponent("formHeader")}
          footer={<UU5.Forms.ContextControls />}
        >
          <CreateListForm onSave={values => this._createList(values)} onCancel={this._cancelForm} />
        </UU5.Forms.ContextModal>
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
