export function options(method, token, body) {
  return {
    method: method,
    mode: 'no-cors',
    headers: {
      'Authorization': 'Bearer ' + token,
      'Accept': 'application/json, text/plain, */*',
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(body),
  };
}

export function url(url) {
  return 'http://localhost:8080' + url;

}
