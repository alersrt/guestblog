import React, {Component} from 'react';
import {deleteMessage} from '../actions/message';
import {getMessages} from '../actions/messages';
import connect from 'react-redux/es/connect/connect';

class Message extends Component {
  render() {
    let date = new Date(this.props.timestamp);
    let id = this.props.id;
    let title = this.props.title;
    let text = this.props.text;
    let file = this.props.file;

    return (
      <div id={'message-id-' + id} className="message">
        <p id="message-title">{title}</p>
        <p id="message-timestamp">{date.toLocaleString()}</p>
        <p id="message-text">{text}</p>
        <img src={file}/>
        <p/>
        <button onClick={() => {
          this.props.deleteMessage(id);
          this.props.getMessages();
        }}>Delete
        </button>
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {};
};

const mapDispatchToProps = (dispatch) => {
  return {
    getMessages: () => dispatch(getMessages()),
    deleteMessage: (id) => dispatch(deleteMessage(id)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Message);
