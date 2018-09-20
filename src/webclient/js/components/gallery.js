import React, {Component} from 'react';
import Message from './message';
import {options as fetchOptions, url as fetchUrl} from '../constants/fetch';

export default class Gallery extends Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: [],
    };
  }

  getMessages() {
    let url = fetchUrl('/api/messages/');
    let options = () => fetchOptions('GET', localStorage.getItem('token'));
    return fetch(url, options())
    .then(response => response.json())
    .then(data => this.setState({messages: data}))
    .catch(error => console.log(error));
  }

  componentDidMount() {
    this.getMessages();
  }

  render() {
    let messages = this.state.messages;

    return <div className="messages">
      {!!messages
        ? messages.map(m => {
          let file = !!m.file ? '/api/files/' + m.file : undefined;
          return <Message id={m.id} timestamp={m.timestamp} title={m.title} text={m.text} file={file}/>;
        })
        : () => {
        }
      }
    </div>;
  }
}
