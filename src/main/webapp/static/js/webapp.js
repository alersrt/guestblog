class Post extends React.Component {
  render() {
    return (
      <div id={this.props.id}>
        <p>{this.props.title}</p>
        <p>{this.props.text}</p>
        <button onClick={() => delPost(this.props.id)}>Delete</button>
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
  instanceAxios.get('/posts/').then(function(response) {
    let posts = response.data['posts'];

    ReactDOM.render(
      posts.map(p => <Post id={p.id} title={p.title} text={p.text}/>),
      document.getElementById('root'),
    );
  }).catch(function(error) {
    console.log(error);
  });
}

function addPost(post, callback) {
  instanceAxios.put('/posts/', {
    title: post.title,
    text: post.text,
  }).
  then(function() {
    callback();
    onLoad();
  }).catch(function(error) {
    console.log(error);
  });
}

function delPost(id) {
  instanceAxios.delete('/posts/', {
    data: {
      id: id,
    },
  }).then(function() {
    onLoad();
  }).catch(function(error) {
    console.log(error);
  });
}
