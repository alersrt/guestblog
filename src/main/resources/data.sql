insert into account(username, authorities, email, password)
values ('admin', '{ADMIN}', null, '$2a$10$qtLc3DFvFW1RzPcteRaJVOz4XeVaxZKjBlESHmJGXvLLmjg9qym3q'),
       ('user', '{USER}', null, '$2a$10$RS2m6YmakNRsCCBri2UVh.OOzjPKcb8czCvgYuthrwv3PO4H8eAbW')
on conflict do nothing;