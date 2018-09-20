import event from '../constants/event';

export function userHasErrored(state = false, action) {
  switch (action.type) {
    case event.user.ERROR:
      return action.hasErrored;

    default:
      return state;
  }
}

export function userIsLoading(state = false, action) {
  switch (action.type) {
    case event.user.LOADING:
      return action.isLoading;

    default:
      return state;
  }
}

export function user(state = null, action) {
  switch (action.type) {
    case event.user.SUCCESS:
      return action.user;

    default:
      return state;
  }
}
