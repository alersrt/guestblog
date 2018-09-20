import event from '../constants/event';

export function messagesHasErrored(bool) {
  return {
    type: event.messages.ERROR,
    hasErrored: bool,
  };
}

export function messagesIsLoading(bool) {
  return {
    type: event.messages.LOADING,
    isLoading: bool,
  };
}

export function messagesFetchSuccess(messages) {
  return {
    type: event.messages.SUCCESS,
    messages,
  };
}

export function getMessages() {
  return (dispatch) => {
    dispatch(messagesIsLoading(true));
    fetch('/api/messages')
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(messagesIsLoading(false));
      return response;
    })
    .then(response => response.json())
    .then(messages => dispatch(messagesFetchSuccess(messages)))
    .catch(() => dispatch(messagesHasErrored(true)));
  };
}
