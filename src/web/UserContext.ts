import {UserDto} from "./dto/UserDto";
import React from "react";

export type UserContextProps = {
    user: UserDto,
    populateUser: Function
}

export const UserContext = React.createContext<Partial<UserContextProps>>({})
