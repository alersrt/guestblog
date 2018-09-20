import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {signIn} from '../../actions/token';

class LoginForm extends Component {
  render() {
    return (
      <div className="bar-element" align="right">
        <p><input id="username-input"/></p>
        <p><input id="password-input"/></p>
        <button onClick={() => this.props.cancel()}>Cancel</button>
        <button onClick={() => this.props.signIn(
          document.getElementById('username-input').value,
          document.getElementById('password-input').value)}>
          Sign In
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
    signIn: (username, password) => dispatch(signIn(username, password)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(LoginForm);
