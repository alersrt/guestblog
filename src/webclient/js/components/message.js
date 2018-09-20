import React, {Component} from 'react';

export default class Message extends Component {
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
        }}>Delete
        </button>
      </div>
    );
  }
}
