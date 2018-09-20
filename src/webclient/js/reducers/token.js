import event from '../constants/event';

export function errorToken(state = false, action) {
  switch (action.type) {
    case event.token.ERROR:
      return action.hasErrored;

    default:
      return state;
  }
}

export function receiveToken(state = false, action) {
  switch (action.type) {
    case event.token.RECEIVE:
      return action.isLoading;

    default:
      return state;
  }
}

export function token(state = localStorage.getItem('TOKEN'), action) {
  switch (action.type) {
    case event.token.ADD:
      localStorage.setItem('TOKEN', action.token);
      return action.token;
    case event.token.REMOVE:
      localStorage.removeItem('TOKEN');
      return null;

    default:
      return state;
  }
}
