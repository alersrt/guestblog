import {applyMiddleware, createStore} from 'redux';
import thunk from 'redux-thunk';
import rootReducer from '../reducers';

export default function configureStore(initialState) {
  const initialToken = {
    token: localStorage.getItem('TOKEN'),
  };

  return createStore(
    rootReducer,
    Object.assign({}, initialState, initialToken),
    applyMiddleware(thunk),
  );
}
