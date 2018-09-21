import {combineReducers} from 'redux';
import {messages, messagesHasErrored, messagesIsLoading} from './messages';
import {messageAdded, messageDeleted} from './message';
import {token} from './token';
import {user, userHasErrored, userIsLoading} from './user';

export default combineReducers({
  messages,
  messagesHasErrored,
  messagesIsLoading,
  messageAdded,
  messageDeleted,
  token,
  user,
  userIsLoading,
  userHasErrored,
});
