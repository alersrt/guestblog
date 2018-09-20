export function userHasErrored(bool) {
  return {
    type: 'USER_HAS_ERRORED',
    hasErrored: bool,
  };
}

export function userIsLoading(bool) {
  return {
    type: 'USER_IS_LOADING',
    isLoading: bool,
  };
}

export function userFetchDataSuccess(user) {
  return {
    type: 'USER_FETCH_DATA_SUCCESS',
    user,
  };
}

export function getUser(token) {
  return (dispatch) => {
    dispatch(userIsLoading(true));
    fetch('http://localhost:8080/api/users/current', {
      method: 'get',
      headers: {
        'Authorization': 'Bearer ' + token,
      },
    })
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(userIsLoading(false));
      return response;
    })
    .then(response => response.json())
    .then(user => dispatch(userFetchDataSuccess(user)))
    .catch(() => dispatch(userHasErrored(true)));
  };
}
