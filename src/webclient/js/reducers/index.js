import {combineReducers} from 'redux';
import {messages, messagesHasErrored, messagesIsLoading} from './messages';
import {messageAdd, messageDeleted} from './message';
import {token} from './token';
import {user, userHasErrored, userIsLoading} from './user';

export default combineReducers({
  messages,
  messagesHasErrored,
  messagesIsLoading,
  messageAdd,
  messageDeleted,
  token,
  user,
  userIsLoading,
  userHasErrored,
});
