import {UserDto} from "./dto/UserDto";
import {Buffer} from "buffer";
import {MessageDto} from "./dto/MessageDto";

const decode = (str: string): string => Buffer.from(str, 'base64').toString('binary');
const encode = (str: string): string => Buffer.from(str, 'binary').toString('base64');

function fetcher(input: RequestInfo, init?: RequestInit): Promise<any> {
  if (!!init) {
    init.headers = {
      ...init.headers,
      'X-Requested-With': 'XMLHttpRequest'
    }
  } else {
    init = {
      headers: {
        'X-Requested-With': 'XMLHttpRequest'
      }
    }
  }
  return fetch(input, init)
}

export async function login(email: string, password: string): Promise<UserDto> {
  const response = await fetcher('/api/auth/login', {
    method: 'POST',
    headers: {
      'Authorization': `Basic ${encode(`${email}:${password}`)}`
    }
  })

  switch (response.status) {
    case 200: {
      return response.json();
    }
    default: {
      throw new Error()
    }
  }
}

export async function logout(): Promise<void> {
  await fetcher('/api/auth/logout')
}

export async function me(): Promise<UserDto> {
  const response = await fetcher('/api/account/me', {
    method: 'GET'
  })

  switch (response.status) {
    case 200: {
      return response.json();
    }
    default: {
      throw new Error('unauthorized')
    }
  }
}

export async function getMessages(): Promise<Array<MessageDto>> {
  const response = await fetcher('/api/message', {
    method: 'GET'
  })

  switch (response.status) {
    case 200: {
      return response.json();
    }
    default: {
      throw new Error()
    }
  }
}

export async function deleteMessage(id: number): Promise<void> {
  const response = await fetcher(`/api/message/${id}`, {
    method: 'DELETE'
  })

  if (response.status != 200) {
    throw new Error()
  }
}

export async function postMessage(title: string, text: string, file: string | Blob): Promise<MessageDto> {
  let formData = new FormData()
  formData.append("file", file);
  formData.append(
      "metadata",
      new Blob([JSON.stringify({title: title, text: text})], {
        type: "application/json",
      }),
      "metadata.json"
  );
  const response = await fetcher(`/api/message`, {
    method: 'POST',
    body: formData
  })
  switch (response.status) {
    case 200: {
      return response.json();
    }
    default: {
      throw new Error()
    }
  }
}
