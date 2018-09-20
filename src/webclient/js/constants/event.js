const event = {
  messages: {
    ERROR: 'MESSAGES_GET_ERROR',
    LOADING: 'MESSAGES_LOADING',
    SUCCESS: 'MESSAGES_GET_SUCCESS',
  },
  message: {
    get: {
      ERROR: 'MESSAGE_GET_ERROR',
      LOADING: 'MESSAGE_LOADING',
      SUCCESS: 'MESSAGE_GET_SUCCESS',
    },
    add: {
      ERROR: 'MESSAGE_ADD_ERROR',
      UPLOADING: 'MESSAGE_UPLOADING',
      SUCCESS: 'MESSAGE_ADD_SUCCESS',
    },
    del: {
      ERROR: 'MESSAGE_DEL_ERROR',
      DELETING: 'MESSAGE_DELETING',
      SUCCESS: 'MESSAGE_DEL_SUCCESS',
    },
  },
  token: {
    ERROR: 'TOKEN_GET_ERROR',
    LOADING: 'TOKEN_LOADING',
    GET: 'TOKEN_GET_SUCCESS',
    REMOVE: 'TOKEN_REMOVED',
  },
  user: {
    ERROR: 'USER_ERROR',
    LOADING: 'USER_LOADING',
    SUCCESS: 'USER_SUCCESS',
  },
};

export default event;
