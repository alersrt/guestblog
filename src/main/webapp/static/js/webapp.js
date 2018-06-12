class Message extends React.Component {
  render() {
    return (
      <div id={this.props.id}>
        <p>{this.props.title}</p>
        <p>{this.props.text}</p>
        <button onClick={() => delMessage(this.props.id)}>Delete</button>
      </div>
    );
  }
}

const instanceAxios = axios.create({
  baseURL: '/api/',
  timeout: 10000,
});

onLoad();

function onLoad() {
  instanceAxios.get('/messages/').then(function(response) {
    let messages = response.data['messages'];

    ReactDOM.render(
      messages.map(p => <Message id={p.id} title={p.title} text={p.text}/>),
      document.getElementById('root'),
    );
  }).catch(function(error) {
    console.log(error);
  });
}

function newMessage() {
  ReactDOM.render(
    <div id="new-message">
      <p/><label htmlFor="message-title">Title:</label><input id="message-title"/>
      <p/><label htmlFor="message-text">Text:</label><textarea id="message-text"></textarea>
      <p/><button onClick={onLoad}>Cancel</button><button onClick={addMessage}>Submit</button>
    </div>,
    document.getElementById('root')
  );
}

function addMessage() {
  instanceAxios.put('/messages/', {
    title: document.getElementById('message-title').value,
    text: document.getElementById('message-text').value,
  }).
  then(function() {
    onLoad();
  }).catch(function(error) {
    console.log(error);
  });
}

function delMessage(id) {
  instanceAxios.delete('/messages/', {
    data: {
      id: id,
    },
  }).then(function() {
    onLoad();
  }).catch(function(error) {
    console.log(error);
  });
}
