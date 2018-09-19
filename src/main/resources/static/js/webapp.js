'use strict';

const instanceAxios = axios.create({
  baseURL: '/api/',
  timeout: 10000,
});
instanceAxios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');

function getMessages() {
  return instanceAxios.get('/messages/').
  then(response => this.setState({messages: response.data})).
  catch(error => console.log(error));
}

class Message extends React.Component {
  delMessage(id) {
    instanceAxios.delete('/messages/' + id).then(() => {
      getMessages();
    }).catch(error => console.log(error));
  }

  render() {
    let date = new Date(this.props.timestamp);
    let id = this.props.id;
    let title = this.props.title;
    let text = this.props.text;
    let file = this.props.file;

    return (
      <div id={'message-id-' + id} className="message">
        <p id="message-title">{title}</p>
        <p id="message-timestamp">{date.toUTCString()}</p>
        <p id="message-text">{text}</p>
        <img src={file}/>
        <p/>
        <button onClick={() => this.delMessage(this.props.id)}>Delete</button>
      </div>
    );
  }
}

class Messages extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      messages: [],
    };

    getMessages = getMessages.bind(this);
    getMessages();
  }

  render() {
    let messages = this.state.messages;

    return <div className="messages">
      {!!messages
        ? messages.map(m => {
          let file = m.file !== undefined ? '/api/files/' + m.file : undefined;
          return <Message id={m.id} timestamp={m.timestamp} title={m.title} text={m.text} file={file}/>;
        })
        : () => {
        }
      }
    </div>;
  }
}

class NewMessage extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      addState: false,
    };
  }

  loadFile() {
    document.getElementById('add-message').disabled = true;
    let files = document.querySelector('input[type=file]').files;
    let reader = new FileReader();
    reader.onloadend = function() {
      document.getElementById('message-file').dataset.dataFile = reader.result.toString();
      document.getElementById('preview').src = reader.result.toString();
      document.getElementById('add-message').disabled = false;
    };
    reader.readAsDataURL(files[0]);
  }

  clearFile() {
    document.getElementById('message-file').value = '';
    document.getElementById('message-file').dataset.dataFile = '';
    document.getElementById('preview').src = '';
  }

  addMessage() {
    instanceAxios.post('/messages/', {
      title: document.getElementById('message-title').value,
      text: document.getElementById('message-text').value,
      file: document.getElementById('message-file').dataset.dataFile,
    }).then(() => {
      this.setState({addState: false});
      getMessages();
    }).catch(function(error) {
      console.log(error);
    });
  }

  render() {
    let newMessageForm = <div id="new-message">
      <p/><label htmlFor="message-title">Title:</label><input id="message-title"/>
      <p/><label htmlFor="message-text">Text:</label><textarea id="message-text"/>
      <p/><input type="file" id="message-file" data-file="" onChange={() => this.loadFile()}/>
      <p/><img id="preview" height="200px" onClick={() => this.clearFile()}/>
      <p/>
      <button onClick={() => this.setState({addState: false})}>Cancel</button>
      <button id="add-message" onClick={() => this.addMessage()}>Submit</button>
    </div>;
    let newMessageButton = <button onClick={() => this.setState({addState: true})}>New Message</button>;

    return (this.state.addState ? newMessageForm : newMessageButton);
  }
}

class LoginForm extends React.Component {
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
    instanceAxios.get('/users/current').
    then(response => {
      if (response.status === 200) {
        this.setState({username: response.data.username});
      } else {
        this.setState({username: undefined});
      }
    }).
    catch(error => console.log(error));
  }

  signIn() {
    instanceAxios.post('/users/sign/in', {
      username: document.getElementById('username-input').value,
      password: document.getElementById('password-input').value,
    }).then(response => {
        let token = response.data.token;
        localStorage.setItem('token', token);
        instanceAxios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
        this.getUser();
        this.setState({signed: true, loginState: false});
      },
    ).catch(error => console.log(error));
  }

  signOut() {
    localStorage.removeItem('token');
    delete instanceAxios.defaults.headers.common['Authorization'];
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
        {[greeting, this.state.loginState ? loginForm : loginButton]}
      </div>
    );
  }
}

class Application extends React.Component {
  render() {
    return ([
      <LoginForm/>,
      <hr/>,
      <NewMessage/>,
      <hr/>,
      <Messages/>,
    ]);
  }
}

ReactDOM.render(<Application/>, document.getElementById('root'));

