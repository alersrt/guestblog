import event from '../constants/event';

export function tokenHasErrored(state = false, action) {
  switch (action.type) {
    case event.token.ERROR:
      return action.hasErrored;

    default:
      return state;
  }
}

export function tokenIsLoading(state = false, action) {
  switch (action.type) {
    case event.token.LOADING:
      return action.isLoading;

    default:
      return state;
  }
}

export function token(state = localStorage.getItem('TOKEN'), action) {
  switch (action.type) {
    case event.token.GET:
      localStorage.setItem('TOKEN', action.token);
      return action.token;
    case event.token.REMOVE:
      localStorage.removeItem('TOKEN');
      return null;

    default:
      return state;
  }
}
