import React, {Component} from 'react';
import {options as fetchOptions} from '../constants/fetch';

export default class Message extends Component {
  delMessage(id) {
    fetch('/api/messages/' + id, fetchOptions('delete')).catch(error => console.log(error));
  }

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
        <button onClick={() => this.delMessage(this.props.id)}>Delete</button>
      </div>
    );
  }
}
