import React, {FC, FormEvent, ReactElement, useCallback, useEffect, useState} from "react";
import {deleteMessage, getMessages, login, logout, me, postMessage} from "./api";
import {UserDto} from "./dto/UserDto";
import {UserContext, UserContextProps} from "./UserContext";
import {MessagesContext, MessagesContextProps} from "./MessagesContext";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faPlus, faSignOutAlt} from '@fortawesome/free-solid-svg-icons'
import {MessageDto} from "./dto/MessageDto";
import {MessageCard} from "./component/MessageCard";
import "./assets/styles/common.css"

export function App(props: any) {

  const LoginForm: FC<any> = (props): ReactElement => {
    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    function handleLogin(event: FormEvent<HTMLFormElement>) {
      login(username, password).then(value => userCtx.populateUser(value))
      event.preventDefault()
    }

    return (
        <UserContext.Consumer>
          {(ctx) => (
              <form onSubmit={handleLogin}>
                <input type={"text"}
                       placeholder={"login..."}
                       onChange={(e) => setUsername(e.target.value)}/>
                <input type={"password"}
                       placeholder={"password..."}
                       onChange={(e) => setPassword(e.target.value)}/>
                <button type={"submit"}>Submit</button>
              </form>
          )}
        </UserContext.Consumer>
    )
  }

  const LogoutForm: FC<any> = (props): ReactElement => {
    function handleLogout() {
      logout().then(() => userCtx.populateUser(null))
    }

    return (
        <UserContext.Consumer>
          {(ctx) => (
              <div>
                {ctx.user.username}
                <button type={"button"} onClick={handleLogout}><FontAwesomeIcon icon={faSignOutAlt}/></button>
              </div>
          )}
        </UserContext.Consumer>
    )
  }

  const PostMessageForm: FC<any> = (props): ReactElement => {

    const initialUploadFileState = () => ({file: null, previewUrl: null, isLoading: false});
    const [uploadFile, setUploadFile] = useState<Object>(initialUploadFileState());
    const [title, setTitle] = useState<string>('');
    const [text, setText] = useState<string>('');

    const handleFileUpload = useCallback(
        function handleFileUpload(e) {
          const file = e.target.files[0];
          const image = new Image();
          const previewUrl = URL.createObjectURL(file);
          image.src = previewUrl;
          setUploadFile({...uploadFile, isLoading: true});

          image.onload = () => {
            setUploadFile({
              ...uploadFile,
              file,
              image: {width: image.width, height: image.height, url: previewUrl},
              isLoading: false,
            });
          }
        },
        [uploadFile]
    );

    const handleUpload = useCallback(
        async function handleUpload() {
          try {
            let postedMessage = await postMessage(title, text, uploadFile.file);
            messagesCtx.populateMessages()
          } catch (e) {
            console.log(e);
            close()
          } finally {
            close()
          }
        },
        [uploadFile, title, text]
    );

    const reset = useCallback(function reset() {
      setUploadFile(initialUploadFileState());
    }, []);

    const close = useCallback(function close() {
      props.close();
    }, []);

    return (
        <MessagesContext.Consumer>
          {(ctx) => (
              <div className={"post-message"} style={{"display": props.display ? "block" : "none"}}>
                <p/><input id="message-title"
                           onChange={(e) => setTitle(e.target.value)}/>
                <p/><textarea id="message-text"
                              onChange={(e) => setText(e.target.value)}/>
                <p/><input type="file" id="message-file" onChange={handleFileUpload}/>
                <img id="preview"
                     style={{"width": "80%", "height": "auto"}}
                     src={uploadFile.image?.url}
                     alt={uploadFile.file?.name}
                     onClick={reset}/>
                <p/>
                <p/>
                <button onClick={close}>Cancel</button>
                <button id="add-message" onClick={handleUpload}>Submit
                </button>
              </div>
          )}
        </MessagesContext.Consumer>
    )
  }

  const [user, setUser] = useState<UserDto>();
  const [messages, setMessages] = useState<Array<MessageDto>>();
  const [showPostForm, setShowPostForm] = useState<Boolean>();

  const content = () => {
    getMessages().then(value => setMessages(value)).catch(reason => setMessages(null))
  }

  const getMe = () => {
    me().then(value => setUser(value)).catch(reason => setUser(null))
  }

  const delMessage = (id: number) => {
    deleteMessage(id).then(() => content());
  }

  useEffect(() => {
    getMe()
    content()
  }, [])

  const userCtx: UserContextProps = {
    user: user,
    populateUser: setUser
  }

  const messagesCtx: MessagesContextProps = {
    messages: messages,
    populateMessages: content,
    pushMessage: (message: MessageDto) => {
      messages.push(message);
      setMessages([...messages])
    }
  }

  return (
      <UserContext.Provider value={userCtx}>
        <MessagesContext.Provider value={messagesCtx}>
          <div className={"body"}>
            <div className={"bar"}>
              {user != null
                  ? <LogoutForm className={"bar-element"}/>
                  : <LoginForm className={"bar-element"}/>
              }
            </div>
            <PostMessageForm display={showPostForm} close={() => setShowPostForm(false)}/>
            <div className={"cards"}>
              {messages != null
                  ? messages.map(m => <MessageCard content={m} delFunc={delMessage}/>)
                  : <div/>}
              <div className={"card"}>
                <button className={"plus"} onClick={() => setShowPostForm(true)}>
                  <FontAwesomeIcon icon={faPlus}/>
                </button>
              </div>
            </div>
          </div>
        </MessagesContext.Provider>
      </UserContext.Provider>
  )
}