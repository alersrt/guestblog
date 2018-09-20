import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {getUser} from '../../actions/user';

class Greeting extends Component {

  componentDidUpdate(prevProps, prevState, snapshot) {
    if (this.props.token !== prevProps.token) {
      this.props.getUser();
    }
  }

  componentDidMount() {
    this.props.getUser();
  }

  render() {
    return (
      <div className="bar-element">
        {!!this.props.user ? 'Hello, ' + this.props.user.username + '!' : 'You are not logged...'}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    token: state.token,
    user: state.user,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    getUser: () => dispatch(getUser()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Greeting);
