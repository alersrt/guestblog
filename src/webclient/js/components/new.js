import React, {Component} from 'react';
import {addMessage} from '../actions/message';
import connect from 'react-redux/es/connect/connect';

class New extends Component {
  constructor(props) {
    super(props);
    this.state = {
      addState: false,
    };
  }

  loadFile() {
    document.getElementById('add-message').disabled = true;
    let files = document.querySelector('input[type=file]').files;
    let reader = new FileReader();
    reader.onloadend = function() {
      document.getElementById('message-file').dataset.dataFile = reader.result.toString();
      document.getElementById('preview').src = reader.result.toString();
      document.getElementById('add-message').disabled = false;
    };
    reader.readAsDataURL(files[0]);
  }

  clearFile() {
    document.getElementById('message-file').value = '';
    document.getElementById('message-file').dataset.dataFile = '';
    document.getElementById('preview').src = '';
  }

  render() {
    let newMessageForm = <div id="new-message">
      <p/><label htmlFor="message-title">Title:</label><input id="message-title"/>
      <p/><label htmlFor="message-text">Text:</label><textarea id="message-text"/>
      <p/><input type="file" id="message-file" data-file="" onChange={() => this.loadFile()}/>
      <p/><img id="preview" height="200px" onClick={() => this.clearFile()}/>
      <p/>
      <button onClick={() => this.setState({addState: false})}>Cancel</button>
      <button id="add-message" onClick={() => this.props.addMessage({
        title: document.getElementById('message-title').value,
        text: document.getElementById('message-text').value,
        file: document.getElementById('message-file').dataset.dataFile,
      })}>Submit
      </button>
    </div>;
    let newMessageButton = <button onClick={() => this.setState({addState: true})}>New Message</button>;

    return (this.state.addState ? newMessageForm : newMessageButton);
  }
}

const mapStateToProps = (state) => {
  return {};
};

const mapDispatchToProps = (dispatch) => {
  return {
    addMessage: (message) => dispatch(addMessage(message)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(New);
