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

export function getUser() {
  return (dispatch) => {
    dispatch(userIsLoading(true));
    fetch('http://localhost:8080/api/users/current', {
      method: 'get',
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('TOKEN'),
      },
    })
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(userIsLoading(false));
      return response;
    })
    .then(response => response.status !== 204 ? response.json() : null)
    .then(user => {
      console.log('actions: ' + user);
      return dispatch(userFetchDataSuccess(user));
    })
    .catch(() => dispatch(userHasErrored(true)));
  };
}
