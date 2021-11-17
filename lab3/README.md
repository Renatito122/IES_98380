## Exercise 1 Questions

# The “UserController” class gets an instance of “userRepository” through its constructor; how is this new repository instantiated?
   The UserController (specifically a creator method in userRepository) creates a new instance of userRepository


# List the methods invoked in the “userRepository” object by the “UserController”. Where are these methods defined?
   The methods below are defined in the CrudRepository, exdended by the userRepository interface:
	userRepository.findAll() 
	userRepository.save() 
	userRepository.findById().orElseThrow() 
	userRepository.delete()


# Where is the databeing saved?
   It is being saved in a H2 database.


# Where is the rule for the “not empty” email address defined?
   It is defined in User.java (when using the tag @NotBlank)




# Review Questions 

## A) Explain the differences between the RestController and Controller components used in different parts of this lab.
   The main difference between Controller component and RestController is in the way the HTTP response body is created. Using the @Controller annotation, it is needed to define the view. Using @RestController, the service simply returns the object data as a JSON/XML.


## C) Explain the annotations @Table, @Colum, @Id foundin the Employee entity.
   @Table: It allows to specify the details of the table that is going to be used to stored all the date in the database.
   @Column: is used to associate the column of the table with a specific field (we can specifiy the name of table, if it can be null, empty, etc).
   @Id: is used to specify some field as the primary key.


## D) Explain the use of the annotation @AutoWired (in the Rest Controller class).
   If we annotate a class with @Autowired, Spring will automatically resolve the instance and inject it into the class that declared it. So, we don’t need to obtain the singleton instance ourselves.


