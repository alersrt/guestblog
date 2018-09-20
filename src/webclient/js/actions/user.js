import event from '../constants/event';

export function errorUser(bool) {
  return {
    type: event.user.ERROR,
    hasErrored: bool,
  };
}

export function receiveUser(bool) {
  return {
    type: event.user.LOADING,
    isLoading: bool,
  };
}

export function successUser(user) {
  return {
    type: event.user.SUCCESS,
    user,
  };
}

export function getUser() {
  return (dispatch) => {
    dispatch(receiveUser(true));
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
      dispatch(receiveUser(false));
      return response;
    })
    .then(response => response.status !== 204 ? response.json() : null)
    .then(user => dispatch(successUser(user)))
    .catch(() => dispatch(errorUser(true)));
  };
}
