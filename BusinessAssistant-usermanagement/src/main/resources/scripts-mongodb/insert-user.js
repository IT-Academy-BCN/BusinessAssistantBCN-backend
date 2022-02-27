// To execute a JavaScript file in mongo shell write:
// load("myfile.js")

// switch to database
use babcn-users;


// Insert a User ONLY
db.users.insertOne({
	"uuid": "e3549460-6f03-4688-846c-0561d9739e9d",
	"email": "user@mail.com",
	"password": "abc123",
	"role": "User"
})


// OR insert User and Admin
db.users.insert([{
	"uuid": "e3549460-6f03-4688-846c-0561d9739e9d",
	"email": "user@mail.com",
	"password": "abc123",
	"role": "User"
},{
  	"uuid": "88aaaeed-4790-4a2a-9f31-e86e1b949f01",
  	"email": "admin@mail.com",
  	"password": "admin123",
  	"role": "Admin"
  }]
)
