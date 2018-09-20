import event from '../constants/event';

function tokenHasErrored(bool) {
  return {
    type: event.token.ERROR,
    hasErrored: bool,
  };
}

function tokenIsLoading(bool) {
  return {
    type: event.token.LOADING,
    isLoading: bool,
  };
}

function tokenGetSuccess(token) {
  return {
    type: event.token.GET,
    token,
  };
}

function tokenRemove() {
  return {
    type: event.token.REMOVE,
  };
}

export function signIn(username, password) {
  return (dispatch) => {
    dispatch(tokenIsLoading(true));
    fetch('/api/users/sign/in', {
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
      dispatch(tokenIsLoading(false));
      return response;
    })
    .then(response => response.json())
    .then(data => dispatch(tokenGetSuccess(data.token)))
    .catch(() => dispatch(tokenHasErrored(true)));
  };
}

export function signOut() {
  return (dispatch) => {
    dispatch(tokenRemove());
  };
}
