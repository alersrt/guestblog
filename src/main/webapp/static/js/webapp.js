const instanceAxios = axios.create({
  baseURL: 'http://localhost:8080/api/',
  timeout: 1000,
});

function onLoad() {
  instanceAxios.get('hello').then(function(response) {
    document.getElementById('hello').innerText = response.data['greeting'];
  }).catch(function(error) {
    console.log(error);
  });
}

onLoad();