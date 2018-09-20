import event from '../constants/event';

function messagesHasErrored(bool) {
  return {
    type: event.messages.ERROR,
    hasErrored: bool,
  };
}

function messagesIsLoading(bool) {
  return {
    type: event.messages.LOADING,
    isLoading: bool,
  };
}

function messagesFetchSuccess(messages) {
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
