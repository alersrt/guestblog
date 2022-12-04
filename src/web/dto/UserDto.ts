export class UserDto {
    constructor(id: number, email: string, authorities: string[], avatar: string) {
        this._id = id;
        this._email = email;
        this._authorities = authorities;
        this._avatar = avatar;
    }

    private _id?: number;

    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
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

    private _avatar: string;

    get avatar(): string {
        return this._avatar;
    }

    set avatar(value: string) {
        this._avatar = value;
    }
}
