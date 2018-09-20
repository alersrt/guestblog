import React, {Component} from 'react';
import Login from './components/login';
import New from './components/new';
import Gallery from './components/gallery';

export default class App extends Component {
  render() {
    return (
      <div>
        <Login/>
        <hr/>
        <New/>
        <hr/>
        <Gallery/>
      </div>
    );
  }
}
