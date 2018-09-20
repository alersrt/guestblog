import event from '../constants/event';

export function messagesHasErrored(state = false, action) {
  switch (action.type) {
    case event.messages.ERROR:
      return action.hasErrored;

    default:
      return state;
  }
}

export function messagesIsLoading(state = false, action) {
  switch (action.type) {
    case event.messages.LOADING:
      return action.isLoading;

    default:
      return state;
  }
}

export function messages(state = [], action) {
  switch (action.type) {
    case event.messages.SUCCESS:
      return action.messages;

    default:
      return state;
  }
}
