import event from '../constants/event';

export function userHasErrored(bool) {
  return {
    type: event.user.ERROR,
    hasErrored: bool,
  };
}

export function userIsLoading(bool) {
  return {
    type: event.user.LOADING,
    isLoading: bool,
  };
}

export function userGetSuccess(user) {
  return {
    type: event.user.SUCCESS,
    user,
  };
}

export function getUser() {
  return (dispatch) => {
    dispatch(userIsLoading(true));
    fetch('/api/users/current', {
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
    .then(user => dispatch(userGetSuccess(user)))
    .catch(() => dispatch(userHasErrored(true)));
  };
}
