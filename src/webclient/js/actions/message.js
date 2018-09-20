import event from '../constants/event';

function messageLoadingHasErrored(boolean) {
  return {
    type: event.message.get.ERROR,
    hasErrored: boolean,
  };
}

function messageIsLoading(boolean) {
  return {
    type: event.message.get.LOADING,
    isDeleting: boolean,
  };
}

function messageLoadingSuccess(message) {
  return {
    type: event.message.add.SUCCESS,
    message,
  };
}

function messageUploadingHasErrored(boolean) {
  return {
    type: event.message.add.ERROR,
    hasErrored: boolean,
  };
}

function messageIsUploading(boolean) {
  return {
    type: event.message.add.UPLOADING,
    isDeleting: boolean,
  };
}

function messageUploadingSuccess(id) {
  return {
    type: event.message.add.SUCCESS,
    id: id,
  };
}

function messageDeletingHasErrored(boolean) {
  return {
    type: event.message.del.ERROR,
    hasErrored: boolean,
  };
}

function messageIsDeleting(boolean) {
  return {
    type: event.message.del.DELETING,
    isDeleting: boolean,
  };
}

function messageDeletingSuccess(id) {
  return {
    type: event.message.del.SUCCESS,
    id: id,
  };
}

export function deleteMessage(id) {
  return (dispatch) => {
    dispatch(messageIsDeleting(true));
    fetch('/api/messages/' + id, {
      method: 'DELETE',
      headers: {
        Authorization: 'Bearer ' + localStorage.getItem('TOKEN'),
      },
    })
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(messageIsDeleting(false));
      return response;
    })
    .then(() => dispatch(messageDeletingSuccess(id)))
    .catch(() => dispatch(messageDeletingHasErrored(true)));
  };
}

export function addMessage(message) {
  return (dispatch) => {
    dispatch(messageIsUploading(true));
    fetch('/api/messages/', {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + localStorage.getItem('TOKEN'),
        'Content-Type': 'application/json',
        'Accept': 'application/json',

      },
      body: JSON.stringify({
        title: message.title,
        text: message.text,
        file: message.file,
      }),
    })
    .then(response => {
      if (!response.ok) {
        throw Error(response.statusText);
      }
      dispatch(messageIsUploading(false));
      return response;
    })
    .then(response => response.json())
    .then(response => dispatch(messageUploadingSuccess(response.id)))
    .catch(() => dispatch(messageUploadingHasErrored(true)));
  };
}
