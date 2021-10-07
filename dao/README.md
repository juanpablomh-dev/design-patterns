## Definition

> Allows separation between two important parts of an application that can but should not know anything of each other, and which can be expected to evolve frequently 
and independently. Changing business logic can rely on the same DAO interface, while changes to persistence logic do not affect DAO clients as long as the interface 
remains correctly implemented.
All details of storage are hidden from the rest of the application. 
Thus, possible changes to the persistence mechanism can be implemented by just modifying one DAO implementation while the rest of the application isn't affected. 
DAOs act as an intermediary between the application and the database. They move data back and forth between objects and database records. 
Unit testing the code is facilitated by substituting the DAO with a test double in the test, thereby making the tests independent of the persistence layer. 

## Example used

There's a set of products that need to be persisted to database. Additionally we need the whole 
set of CRUD (create/read/update/delete) operations so we can operate on productseasily.
