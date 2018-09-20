import React, {Component} from 'react';
import Message from './message';
import {connect} from 'react-redux';
import {messagesFetchData} from '../actions/messages';

class Gallery extends Component {
  componentDidMount() {
    this.props.fetchData();
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
        return <Message key={m.id} timestamp={m.timestamp} title={m.title} text={m.text} file={file}/>;
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
    fetchData: () => dispatch(messagesFetchData()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Gallery);
