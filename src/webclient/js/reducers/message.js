import event from '../constants/event';

export function messageDeleted(state = null, action) {
  switch (action.type) {
    case event.message.del.SUCCESS:
      return action.id;

    default:
      return state;
  }
}

export function messageAdded(state = null, action) {
  switch (action.type) {
    case event.message.add.SUCCESS:
      return action.id;

    default:
      return state;
  }
}
