import event from '../constants/event';

export function errorMessages(bool) {
  return {
    type: event.messages.ERROR,
    hasErrored: bool,
  };
}

export function receiveMessages(bool) {
  return {
    type: event.messages.LOADING,
    isLoading: bool,
  };
}

export function successMessages(messages) {
  return {
    type: event.messages.SUCCESS,
    messages,
  };
}

export function getMessages() {
  return (dispatch) => {
    dispatch(receiveMessages(true));
    fetch('http://localhost:8080/api/messages')
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(receiveMessages(false));
      return response;
    })
    .then(response => response.json())
    .then(messages => dispatch(successMessages(messages)))
    .catch(() => dispatch(errorMessages(true)));
  };
}
