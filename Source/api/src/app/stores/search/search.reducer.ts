import * as ResultActions from './search.actions';

export type Action = ResultActions.All;


const defaultState: string = '';

export function SearchReducer(state: string = defaultState, action: Action) {
  switch (action.type ) {
    case ResultActions.SETTERM:
      return action.payload;
    default:
      return state;
  }
}
