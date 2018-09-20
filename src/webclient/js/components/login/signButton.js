import React, {Component} from 'react';
import connect from 'react-redux/es/connect/connect';
import {signOut} from '../../actions/token';

class SignButton extends Component {
  render() {
    return (
      <div className="bar-element" align="right">
        {!!this.props.token
          ? <button onClick={() => this.props.signOut()}>Sign Out</button>
          : <button onClick={() => this.props.loginForm()}>Sign In</button>}
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
    signOut: () => dispatch(signOut()),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(SignButton);
