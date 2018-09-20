import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {signIn, signOut} from '../actions/token';
import Greeting from './login/greeting';
import LoginForm from './login/loginForm';
import SignButton from './login/signButton';

class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loginState: false,
    };
  }

  componentDidUpdate(prevState) {
    if (this.props.token !== prevState.token) {
      this.setState({loginState: false});
    }
  }

  render() {
    let loginForm = <LoginForm cancel={() => this.setState({loginState: false})}/>;
    let signButton = <SignButton loginForm={() => this.setState({loginState: true})}/>;

    return (
      <div className="bar">
        <Greeting/>
        {this.state.loginState ? loginForm : signButton}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    token: state.token,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    signIn: (username, password) => dispatch(signIn(username, password)),
    signOut: () => dispatch(signOut()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Login);
