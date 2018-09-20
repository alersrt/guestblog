export function messagesHasErrored(bool) {
  return {
    type: 'MESSAGES_HAS_ERRORED',
    hasErrored: bool,
  };
}

export function messagesIsLoading(bool) {
  return {
    type: 'MESSAGES_IS_LOADING',
    isLoading: bool,
  };
}

export function messagesFetchDataSuccess(messages) {
  return {
    type: 'MESSAGES_FETCH_DATA_SUCCESS',
    messages,
  };
}

export function messagesFetchData() {
  return (dispatch) => {
    dispatch(messagesIsLoading(true));
    fetch('http://localhost:8080/api/messages')
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(messagesIsLoading(false));
      return response;
    })
    .then(response => response.json())
    .then(messages => dispatch(messagesFetchDataSuccess(messages)))
    .catch(() => dispatch(messagesHasErrored(true)));
  };
}
