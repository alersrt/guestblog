export function messagesHasErrored(state = false, action) {
  switch (action.type) {
    case 'MESSAGES_HAS_ERRORED':
      return action.hasErrored;

    default:
      return state;
  }
}

export function messagesIsLoading(state = false, action) {
  switch (action.type) {
    case 'MESSAGES_IS_LOADING':
      return action.isLoading;

    default:
      return state;
  }
}

export function messages(state = [], action) {
  switch (action.type) {
    case 'MESSAGES_FETCH_DATA_SUCCESS':
      return action.messages;

    default:
      return state;
  }
}
