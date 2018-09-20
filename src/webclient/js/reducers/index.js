import {combineReducers} from 'redux';
import {messages, messagesHasErrored, messagesIsLoading} from './messages';
import {token, tokenHasErrored, tokenIsLoading} from './token';
import {user, userHasErrored, userIsLoading} from './user';

export default combineReducers({
  messages,
  messagesHasErrored,
  messagesIsLoading,
  token,
  tokenIsLoading,
  tokenHasErrored,
  user,
  userIsLoading,
  userHasErrored,
});
