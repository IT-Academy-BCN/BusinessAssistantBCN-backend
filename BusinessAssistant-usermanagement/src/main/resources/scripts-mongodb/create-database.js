//show databases
show dbs;

// create database
use babcn-users

// create collection
db.createCollection("users")

// to read
db.users.find({}).pretty();