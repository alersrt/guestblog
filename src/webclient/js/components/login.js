import React, {Component} from 'react';
import {options as fetchOptions, url as fetchUrl} from '../constants/fetch';

export default class Login extends Component {
  constructor(props) {
    super(props);
    this.state = {
      loginState: false,
      signed: localStorage.hasOwnProperty('token'),
      username: undefined,
    };
    this.getUser();
  }

  getUser() {
    let url = fetchUrl('/api/users/current');
    let options = () => fetchOptions('GET', localStorage.getItem('token'));
    fetch(url, options())
    .then(response => {
      return {status: response.ok, data: response.json()}
    })
    .then(response => {
      if (response.status) {
        this.setState({username: response.data.username});
      } else {
        this.setState({username: undefined});
      }

    })
    .catch(error => console.log(error));
  }

  signIn() {
    let url = fetchUrl('/api/users/sign/in');
    let options = fetchOptions('POST', localStorage.getItem('token'), {
      username: document.getElementById('username-input').value,
      password: document.getElementById('password-input').value,
    });
    fetch(url, options)
    .then(response => {
      let token = response.data.token;
      localStorage.setItem('token', token);
      this.getUser();
      this.setState({signed: true, loginState: false});
    })
    .catch(error => console.log(error));
  }

  signOut() {
    localStorage.removeItem('token');
    this.getUser();
    this.setState({signed: false});
  }

  render() {
    let greeting = this.state.username !== undefined
      ? <div className="bar-element">Hello, {this.state.username}!</div>
      : <div className="bar-element">You are not logged...</div>;
    let loginForm = <div className="bar-element" align="right">
      <p><label htmlFor="username-input">Username:</label><input id="username-input"/></p>
      <p><label htmlFor="password-input">Password:</label><input id="password-input"/></p>
      <button onClick={() => this.setState({loginState: false})}>Cancel</button>
      <button onClick={() => this.signIn()}>Sign In</button>
    </div>;
    let loginButton = this.state.signed
      ? <div className="bar-element" align="right">
        <button onClick={() => this.signOut()}>Sign Out</button>
      </div>
      : <div className="bar-element" align="right">
        <button onClick={() => this.setState({loginState: true})}>Sign In</button>
      </div>;

    return (
      <div className="bar">
        {greeting}{this.state.loginState ? loginForm : loginButton}
      </div>
    );
  }
}
