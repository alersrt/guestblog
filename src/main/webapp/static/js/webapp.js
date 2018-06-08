const instanceAxios = axios.create({
  baseURL: '/api/',
  timeout: 10000,
});

function onLoad() {
  instanceAxios.get('/posts/').then(function(response) {
    let posts = response.data['posts'];

    posts.forEach(function(p) {
      ReactDOM.render(
        <div id={p.id}>
          <div>{p.title}</div>
          <div>{p.text}</div>
        </div>,
        document.getElementById('root')
      );
    });
  }).catch(function(error) {
    console.log(error);
  });
}

onLoad();
