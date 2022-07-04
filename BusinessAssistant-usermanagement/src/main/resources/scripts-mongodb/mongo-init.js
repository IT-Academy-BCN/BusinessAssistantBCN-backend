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
db.users.insertMany([
    {
        uuid: 'b0fe0ac0-8bb3-4338-ba13-b744bdffe008',
        email: 'justo.nec@google.org',
        pwd: 'LJT77RVE3TV',
        role: ['ADMIN']
    },
    {
        uuid: '330695cb-de30-4254-9e79-faa7bcb8e9cd',
        email: 'natoque@yahoo.com',
        pwd: 'ECG30VWU4FL',
        role: ['USER']
    },
    {
        uuid: 'c413fff7-b0e8-420c-90bf-1f614bbacb38',
        email: 'nulla.dignissim@outlook.edu',
        pwd: 'BLL17PFD2ML',
        role: ['USER']
    },
    {
        uuid: 'c0e861de-f54b-46db-a396-11c1d4cf3cce',
        email: 'diam.pellentesque@protonmail.edu',
        pwd: 'WLE87OEF8KQ',
        role: ['USER']
    },
    {
        uuid: 'fd6f6ae0-4372-495c-9061-881127f1a98f',
        email: 'eu.neque@aol.edu',
        pwd: 'MWB95QCS2LL',
        role: ['USER']
    }
]);

