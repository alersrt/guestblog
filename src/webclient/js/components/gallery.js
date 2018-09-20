import React, {Component} from 'react';
import Message from './message';
import {connect} from 'react-redux';
import {getMessages} from '../actions/messages';

class Gallery extends Component {
  componentDidMount() {
    this.props.getMessages();
  }

  render() {
    if (this.props.hasErrored) {
      return <p>Sorry! There was an error loading the items</p>;
    }

    if (this.props.isLoading) {
      return <p>Loading…</p>;
    }

    let messages = this.props.messages;
    return <div className="messages">
      {messages.map(m => {
        let file = !!m.file ? '/api/files/' + m.file : undefined;
        return <Message key={m.id} id={m.id} timestamp={m.timestamp} title={m.title} text={m.text} file={file}/>;
      })}
    </div>;
  }
}

const mapStateToProps = (state) => {
  return {
    messages: state.messages,
    hasErrored: state.messagesHasErrored,
    isLoading: state.messagesIsLoading,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getMessages: () => dispatch(getMessages()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Gallery);
