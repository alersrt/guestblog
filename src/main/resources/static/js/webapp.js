const instanceAxios = axios.create({
  baseURL: '/api/',
  timeout: 10000,
});

class Message extends React.Component {
  render() {
    return (
      <div id={this.props.id}>
        <p>{this.props.title}</p>
        <p>{this.props.timestamp}</p>
        <p>{this.props.text}</p>
        <img src={this.props.file} width="200px"/>
        <p/>
        <button onClick={() => delMessage(this.props.id)}>Delete</button>
      </div>
    );
  }
}

onLoad();

function onLoad() {
  instanceAxios.get('/messages/').then(function(response) {
    let messages = response.data;
    ReactDOM.render(
      <div>
        <div id="util-root">
          <button onClick={newMessage}>Add message</button>
        </div>
        <hr/>
        <div>
          {messages.map(p => <Message id={p.id} timestamp={p.timestamp} title={p.title} text={p.text}
                                      file={
                                        p.file !== undefined ?
                                          p.file.filename !== undefined ? '/api/files/' + p.file.filename
                                            : undefined
                                          : undefined
                                      }/>)
          }
        </div>
      </div>,
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
      <p/><input type="file" id="message-file" data-file="" onChange={loadFile}/>
      <p/><img id="preview" height="200px" onClick={clearFile}/>
      <p/>
      <button onClick={onCancelAddMessage}>Cancel</button>
      <button id="add-message" onClick={addMessage}>Submit</button>
    </div>,
    document.getElementById('util-root'),
  );
}

function onCancelAddMessage() {
  ReactDOM.render(
    <button onClick={newMessage}>Add message</button>,
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
    file: {
      filename: document.getElementById('message-file').value,
      data: document.getElementById('message-file').dataset.dataFile,
    },
  }).then(function() {
    onCancelAddMessage();
    onLoad();
  }).catch(function(error) {
    console.log(error);
  });
}

function delMessage(id) {
  instanceAxios.delete('/messages/' + id).then(function() {
    onLoad();
  }).catch(function(error) {
    console.log(error);
  });
}
