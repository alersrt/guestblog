insert into account(id, email, authorities)
values (1, 'admin@test.dev', '{ADMIN}'),
       (2, 'user@test.dev', '{USER}')
on conflict do nothing;
select setval('account_id_seq', (select max(id) + 1 from account));

insert into passport(id, account_id, type, hash, created, updated)
values (1, 1, 'PASSWORD', '$2a$10$qtLc3DFvFW1RzPcteRaJVOz4XeVaxZKjBlESHmJGXvLLmjg9qym3q', now(), now()),
       (2, 2, 'PASSWORD', '$2a$10$RS2m6YmakNRsCCBri2UVh.OOzjPKcb8czCvgYuthrwv3PO4H8eAbW', now(), now())
on conflict do nothing;
select setval('passport_id_seq', (select max(id) + 1 from passport));