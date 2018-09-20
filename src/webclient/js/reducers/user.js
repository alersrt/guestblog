export function userHasErrored(state = false, action) {
  switch (action.type) {
    case 'USER_HAS_ERRORED':
      return action.hasErrored;

    default:
      return state;
  }
}

export function userIsLoading(state = false, action) {
  switch (action.type) {
    case 'USER_IS_LOADING':
      return action.isLoading;

    default:
      return state;
  }
}

export function user(state = null, action) {
  switch (action.type) {
    case 'USER_FETCH_DATA_SUCCESS':
      console.log("reducers: " + action.user)
      return action.user;

    default:
      return state;
  }
}
