export function tokenHasErrored(state = false, action) {
  switch (action.type) {
    case 'TOKEN_HAS_ERRORED':
      return action.hasErrored;

    default:
      return state;
  }
}

export function tokenIsLoading(state = false, action) {
  switch (action.type) {
    case 'TOKEN_IS_LOADING':
      return action.isLoading;

    default:
      return state;
  }
}

export function token(state = localStorage.getItem('TOKEN'), action) {
  switch (action.type) {
    case 'TOKEN_FETCH_DATA_SUCCESS':
      localStorage.setItem('TOKEN', action.token);
      break;
    case 'TOKEN_LOGOUT':
      localStorage.removeItem('TOKEN');
      break;
  }
  return state;
}
