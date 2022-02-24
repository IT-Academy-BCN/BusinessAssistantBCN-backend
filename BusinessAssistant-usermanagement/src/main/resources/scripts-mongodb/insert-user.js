
// insert a user

db.users.insertOne({	
	"uuid": "1",
	"email": "user@mail.com",
	"pwd": "abc123",
	"role": "user"
},{
  	"uuid": "2",
  	"email": "admii@mail.com",
  	"pwd": "admin123",
  	"role": "admin"
  }
)



