import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {signIn, signOut} from '../actions/token';
import {getUser} from '../actions/user';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loginState: false,
    };
  }

  componentDidMount() {
    this.props.getUser(this.props.token);
  }

  render() {
    let greeting = !!this.props.user
      ? <div className="bar-element">Hello, {this.props.user.username}!</div>
      : <div className="bar-element">You are not logged...</div>;
    let loginForm = <div className="bar-element" align="right">
      <p><label htmlFor="username-input">Username:</label><input id="username-input"/></p>
      <p><label htmlFor="password-input">Password:</label><input id="password-input"/></p>
      <button onClick={() => this.setState({loginState: false})}>Cancel</button>
      <button onClick={() => {
        let username = document.getElementById('username-input').value;
        let password = document.getElementById('password-input').value;
        this.props.signIn(username, password);
      }}>Sign In
      </button>
    </div>;
    let loginButton = !!this.props.token
      ? <div className="bar-element" align="right">
        <button onClick={() => this.signOut()}>Sign Out</button>
      </div>
      : <div className="bar-element" align="right">
        <button onClick={() => this.setState({loginState: true})}>Sign In</button>
      </div>;

    return (
      <div className="bar">
        {greeting}
        {this.state.loginState ? loginForm : loginButton}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    token: state.token,
    hasErrored: state.tokenHasErrored,
    isLoading: state.tokenIsLoading,
    user: state.user,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    signIn: (username, password) => dispatch(signIn(username, password)),
    signOut: () => dispatch(signOut()),
    getUser: (token) => dispatch(getUser(token)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
