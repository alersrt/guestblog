import React from "react";
import {MessageDto} from "./dto/MessageDto";

export type MessagesContextProps = {
    messages: Array<MessageDto>,
    populateMessages: Function,
    pushMessage: Function
}

export const MessagesContext = React.createContext<Partial<MessagesContextProps>>({})
