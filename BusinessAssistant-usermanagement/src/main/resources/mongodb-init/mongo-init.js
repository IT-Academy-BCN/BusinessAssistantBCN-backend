db.createUser({
    user: 'admin_businessassistantbcn',
    pwd: 'UhWQQYFVBx95W7',
    roles: [
        {
            role: 'dbOwner',
            db: 'babcn-users',
        },
    ],
    latest_acces : '111111111111' //'System.currentTimeMillis()'
});

db.createCollection("users");

