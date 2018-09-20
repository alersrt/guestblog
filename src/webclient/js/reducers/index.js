import {combineReducers} from 'redux';
import {errorMessages, messages, receiveMessages} from './messages';
import {errorToken, receiveToken, token} from './token';
import {errorUser, receiveUser, user} from './user';

export default combineReducers({
  messages,
  errorMessages,
  receiveMessages,
  token,
  receiveToken,
  errorToken,
  user,
  receiveUser,
  errorUser,
});
