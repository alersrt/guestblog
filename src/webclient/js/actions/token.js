export function tokenHasErrored(bool) {
  return {
    type: 'TOKEN_HAS_ERRORED',
    hasErrored: bool,
  };
}

export function tokenIsLoading(bool) {
  return {
    type: 'TOKEN_IS_LOADING',
    isLoading: bool,
  };
}

export function tokenFetchDataSuccess(token) {
  return {
    type: 'TOKEN_FETCH_DATA_SUCCESS',
    token,
  };
}

export function tokenLogout() {
  return {
    type: 'TOKEN_LOGOUT',
  };
}

export function signIn(username, password) {
  return (dispatch) => {
    dispatch(tokenIsLoading(true));
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
      dispatch(tokenIsLoading(false));
      return response;
    })
    .then(response => response.json())
    .then(data => dispatch(tokenFetchDataSuccess(data.token)))
    .catch(() => dispatch(tokenHasErrored(true)));
  };
}

export function signOut() {
  return (dispatch) => {
    dispatch(tokenLogout());
  };
}
