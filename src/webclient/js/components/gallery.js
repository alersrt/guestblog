import React, {Component} from 'react';
import Message from './message';
import {connect} from 'react-redux';
import {getMessages} from '../actions/messages';

class Gallery extends Component {
  componentDidMount() {
    this.props.getMessages();
  }

  componentDidUpdate(prevProps) {
    if (((prevProps.messageDeleted !== this.props.messageDeleted)
      && prevProps.messages.map(m => m.id).includes(this.props.messageDeleted))
      || ((prevProps.messageAdded !== this.props.messageAdded)
        && !prevProps.messages.map(m => m.id).includes(this.props.messageAdded))) {

      this.props.getMessages();
    }
  };

  render() {
    if (this.props.hasErrored) {
      return <p>Sorry! There was an error loading the items</p>;
    }

    if (this.props.isLoading) {
      return <p>Loading…</p>;
    }

    return <div className="messages">
      {this.props.messages.map(m => {
        let file = !!m.file ? '/api/files/' + m.file : undefined;
        return <Message key={m.id} id={m.id} timestamp={m.timestamp} title={m.title} text={m.text} file={file}/>;
      })}
    </div>;
  }
}

const mapStateToProps = (state) => {
  return {
    messageAdded: state.messageAdded,
    messageDeleted: state.messageDeleted,
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
