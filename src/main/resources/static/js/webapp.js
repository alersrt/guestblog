'use strict';

const instanceAxios = axios.create({
  baseURL: '/api/',
  timeout: 10000,
});
instanceAxios.defaults.headers.common['Authorization'] = 'Bearer ' + localStorage.getItem('token');

class Message extends React.Component {
  render() {
    return (
      <div id={this.props.id} className="inline">
        <p><b>{this.props.title}</b></p>
        <p><i>{this.props.timestamp}</i></p>
        <p>{this.props.text}</p>
        <a href={this.props.file}>
          <img src={this.props.file} width="200px"/>
        </a>
        <p/>
        <button onClick={() => delMessage(this.props.id)}>Delete</button>
      </div>
    );
  }
}

class NewMessageForm extends React.Component {
  render() {
    return (
      <div id="new-message">
        <p/><label htmlFor="message-title">Title:</label><input id="message-title"/>
        <p/><label htmlFor="message-text">Text:</label><textarea id="message-text"></textarea>
        <p/><input type="file" id="message-file" data-file="" onChange={loadFile}/>
        <p/><img id="preview" height="200px" onClick={clearFile}/>
        <p/>
        <button onClick={hideNewMessageForm}>Cancel</button>
        <button id="add-message" onClick={addMessage}>Submit</button>
      </div>
    );
  }
}

class LoginForm extends React.Component {
  render() {
    return (
      <div>
        <p><label htmlFor="username-input">Username:</label><input id="username-input"/></p>
        <p><label htmlFor="password-input">Password:</label><input id="password-input"/></p>
        <button onClick={() => hideLogin()}>Cancel</button>
        <button onClick={() => signIn()}>Submit</button>
      </div>
    );
  }
}

onLoad().then(showGreeting()).then(showMessages()).catch(error => console.log(error));

async function onLoad() {
  return ReactDOM.render(
    <div>
      <div className="bar">
        <div id="greeting" className="inline"/>
        <div id="login" className="inline" align="right">
          <div>{login()}</div>
        </div>
      </div>
      <hr/>
      <div id="util-root">
        <button onClick={() => showNewMessageForm()}>Add message</button>
      </div>
      <hr/>
      <div id="messages"/>
    </div>,
    document.getElementById('root'),
  );
}

async function getUser() {
  return instanceAxios.get('/users/current').then(response => response.data).catch(error => console.log(error));
}

function login() {
  return localStorage.hasOwnProperty('token')
    ? <button onClick={() => signOut()}>SignOut</button>
    : <button onClick={() => showLogin()}>SignIn</button>;
}

async function signIn() {
  instanceAxios.post('/users/signin', {
    username: document.getElementById('username-input').value,
    password: document.getElementById('password-input').value,
  }).then(response => {
      let token = response.data.token;
      localStorage.setItem('token', token);
      instanceAxios.defaults.headers.common['Authorization'] = 'Bearer ' + token;
      hideLogin();
      showGreeting().catch(error => console.log(error));
    },
  ).catch(error => console.log(error));
}

async function signOut() {
  localStorage.removeItem('token');
  delete instanceAxios.defaults.headers.common['Authorization'];
  hideLogin();
  showGreeting().catch(error => console.log(error));
}

async function getMessages() {
  return instanceAxios.get('/messages/').then(response => response.data).catch(error => console.log(error));
}

async function showGreeting() {
  let user = await getUser();

  return ReactDOM.render(
    <div>
      <p>Hello, <b>{user.username}!</b></p>
    </div>,
    document.getElementById('greeting'),
  );
}

async function showMessages() {
  let messages = await getMessages();

  return ReactDOM.render(
    <div className="messages">
      {
        messages.map(p => {
          let file = p.file !== undefined ? '/api/files/' + p.file : undefined;
          return <Message id={p.id} timestamp={p.timestamp} title={p.title} text={p.text} file={file}/>;
        })
      }
    </div>,
    document.getElementById('messages'),
  );
}

function showLogin() {
  ReactDOM.render(
    <LoginForm/>,
    document.getElementById('login'),
  );
}

function hideLogin() {
  ReactDOM.render(
    <div>{login()}</div>,
    document.getElementById('login'),
  );
}

function showNewMessageForm() {
  ReactDOM.render(
    <NewMessageForm/>,
    document.getElementById('util-root'),
  );
}

function hideNewMessageForm() {
  ReactDOM.render(
    <button onClick={() => showNewMessageForm()}>Add message</button>,
    document.getElementById('util-root'),
  );
}

function loadFile() {
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

function clearFile() {
  document.getElementById('message-file').value = '';
  document.getElementById('message-file').dataset.dataFile = '';
  document.getElementById('preview').src = '';
}

function addMessage() {
  instanceAxios.post('/messages/', {
    title: document.getElementById('message-title').value,
    text: document.getElementById('message-text').value,
    file: document.getElementById('message-file').dataset.dataFile,
  }).then(function() {
    hideNewMessageForm();
    showMessages().catch(error => console.log(error));
  }).catch(function(error) {
    console.log(error);
  });
}

function delMessage(id) {
  instanceAxios.delete('/messages/' + id).then(() => showMessages()).catch(error => console.log(error));
}
