db.createUser({
    user: 'admin_businessassistantbcn',
    pwd: 'UhWQQYFVBx95W7',
    roles: [
        {
            role: 'dbOwner',
            db: 'babcn-users',
        },
    ],
});
/*
use babcn-users;
db.createCollection("users");
db.users.insertOne({
    uuid: "26977eee-89f8-11ec-a8a3-0242ac120002",
    email: "user@mail.com",
    pwd: "abc123",
    role: ["user","admin"]
});
*/
 