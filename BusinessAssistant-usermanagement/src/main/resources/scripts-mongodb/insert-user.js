
// insert a user

db.users.insertOne({	
	uuid: "26977eee-89f8-11ec-a8a3-0242ac120002",
	email: "user@mail.com",
	pwd: "abc123",
	role: ["USER","ADMIN"]
})



