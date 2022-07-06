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

db.createCollection("users");

