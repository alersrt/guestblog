import event from '../constants/event';

export function errorToken(bool) {
  return {
    type: event.token.ERROR,
    hasErrored: bool,
  };
}

export function receiveToken(bool) {
  return {
    type: event.token.RECEIVE,
    isLoading: bool,
  };
}

export function addToken(token) {
  return {
    type: event.token.ADD,
    token,
  };
}

export function removeToken() {
  return {
    type: event.token.REMOVE,
  };
}

export function signIn(username, password) {
  return (dispatch) => {
    dispatch(receiveToken(true));
    fetch('http://localhost:8080/api/users/sign/in', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json',
      },
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    })
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(receiveToken(false));
      return response;
    })
    .then(response => response.json())
    .then(data => dispatch(addToken(data.token)))
    .catch(() => dispatch(errorToken(true)));
  };
}

export function signOut() {
  return (dispatch) => {
    dispatch(removeToken());
  };
}
