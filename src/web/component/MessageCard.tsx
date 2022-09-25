import React, {FC} from "react";
import {MessageDto} from "../dto/MessageDto";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faTrashAlt} from '@fortawesome/free-solid-svg-icons'
import moment from "moment";

export type MessageCardProps = {
  content: MessageDto,
  delFunc: Function
}

export function MessageCard(props: MessageCardProps): FC<any> {

  const message = props.content

  return (
      <div id={`message-id-${message.id}`} className={"card"}>
        <p id={"message-title"}>{message.title}</p>
        <p id={"message-timestamp"}>{moment(message.createdAt + 'Z').toNow()}</p>
        {!!message.file ? <img src={`/api/file/${message.file}`}/> : <div/>}
        <p id={"message-text"}>{message.text}</p>
        <button onClick={() => props.delFunc(message.id)}><FontAwesomeIcon icon={faTrashAlt}/></button>
      </div>
  )
}