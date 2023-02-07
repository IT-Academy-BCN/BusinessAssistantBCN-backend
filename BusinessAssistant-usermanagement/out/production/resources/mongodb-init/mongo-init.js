db.createUser({
    user: 'admin_businessassistantbcn',
    pwd: 'UhWQQYFVBx95W7',
    roles: [
        {
            role: 'dbOwner',
            db: 'babcn-users',
        },
    ],
    latest_access : 11111111111, //System.currentTimeMillis()
});

db.createCollection("users");

