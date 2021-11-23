export class UserDto {
  constructor(id: number, username: string, email: string, authorities: string[]) {
    this._id = id;
    this._username = username;
    this._email = email;
    this._authorities = authorities;
  }

  private _id?: number;

  get id(): number {
    return this._id;
  }

  set id(value: number) {
    this._id = value;
  }

  private _username: string;

  get username(): string {
    return this._username;
  }

  set username(value: string) {
    this._username = value;
  }

  private _email: string;

  get email(): string {
    return this._email;
  }

  set email(value: string) {
    this._email = value;
  }

  private _authorities: string[];

  get authorities(): string[] {
    return this._authorities;
  }

  set authorities(value: string[]) {
    this._authorities = value;
  }
}