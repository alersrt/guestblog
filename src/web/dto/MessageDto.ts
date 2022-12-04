export class MessageDto {

    private _id: number;
    private _authorId: number;
    private _title: string;
    private _text: string;
    private _createdAt: Date;
    private _editedAt: Date;
    private _file: string;

    constructor(id: number, authorId: number, title: string, text: string, createdAt: Date, editedAt: Date, file: string) {
        this._id = id;
        this._authorId = authorId;
        this._title = title;
        this._text = text;
        this._createdAt = createdAt;
        this._editedAt = editedAt;
        this._file = file;
    }


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get authorId(): number {
        return this._authorId;
    }

    set authorId(value: number) {
        this._authorId = value;
    }

    get title(): string {
        return this._title;
    }

    set title(value: string) {
        this._title = value;
    }

    get text(): string {
        return this._text;
    }

    set text(value: string) {
        this._text = value;
    }

    get createdAt(): Date {
        return this._createdAt;
    }

    set createdAt(value: Date) {
        this._createdAt = value;
    }

    get editedAt(): Date {
        return this._editedAt;
    }

    set editedAt(value: Date) {
        this._editedAt = value;
    }

    get file(): string {
        return this._file;
    }

    set file(value: string) {
        this._file = value;
    }
}
