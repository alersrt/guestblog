import {combineReducers} from 'redux';
import {messages, messagesHasErrored, messagesIsLoading} from './messages';
import {errorToken, receiveToken, token} from './token';
import {user, userHasErrored, userIsLoading} from './user';

export default combineReducers({
  messages,
  messagesHasErrored,
  messagesIsLoading,
  token,
  receiveToken,
  errorToken,
  user,
  userIsLoading,
  userHasErrored,
});
