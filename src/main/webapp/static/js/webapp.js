const instanceAxios = axios.create({
  baseURL: '/api/',
  timeout: 10000,
});

onLoad();

function onLoad() {
  instanceAxios.get('/posts/').then(function(response) {
    let posts = response.data['posts'];

    posts.forEach(function(p) {
      ReactDOM.render(
        <div class="card-columns">
          <div id={p.id} className="card border rounded">
            <div className="card-body">
              <div className="card-header">{p.title}</div>
              <div className="card-text">{p.text}</div>
              <div className="card-footer">
                <button className="btn btn-secondary" onClick={() => delPost(p.id)}>Delete</button>
              </div>
            </div>
          </div>
        </div>,
        document.getElementById('root'),
      );
    });
  }).catch(function(error) {
    console.log(error);
  });
}

function addPost() {
  instanceAxios.put('/posts/', {
    title: document.getElementById('post-title').value,
    text: document.getElementById('post-text').value,
  }).
  then(function () {
    $('#add-post').modal('hide');
    onLoad();
    console.log(document.getElementById('post-title').textContent)
  }).catch(function(error) {
    console.log(error);
  });
}

function delPost(id) {
  instanceAxios.delete('/posts/', {
    data: {
      id: id,
    },
  }).then(function () {
    onLoad()
  }).catch(function(error) {
    console.log(error);
  });
}
