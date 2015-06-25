## Pair Programming Database System Hack @ Shopify!

### To run

NOTE: you must have a Java Runtime Envrionment setup on your machine to run this application: https://www.java.com/en/download/faq/java_mac.xml

1. compile code using the make file provided:
```$ make ```
2. execute the server via terminal using the API outlined below:
```
$ java DBMS write <DATA>    // returns an ID representing the primary key of the record you just inserted
$ java DBMS list            // will return all the records in the database 
$ java DBMS read <KEY>      // will return the record associated with the specified key 
$ java DBMS find <DATA>     // under construction
```
NOTE: be careful **NOT** to run the application by specifying the class file: 
```java DBMS.class (...) ``` 
As this will result in a java.lang.NoClassDefFoundError

### Notes

* When you run the application, a file called **ShopifyDB** will be created within the same directory you executed the code from. This file represents our database and contains our primary key index. Feel free to keep deleting this file for testing purposes.
* The data field is set to a specific size which can be changed by modifying one of the constants _(DataRecord.DATA_CLENGTH)_ in the code. Currently, it is set to 14 characters. This is how we create our indices, by mapping memory to disk using file pointers. 
* The find method is under construction

### Example test run
```
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS write Amir
ID: 0
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS write Matt
ID: 1
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS write Shopify
ID: 2
Amirs-MacBook-Air-2:ShopifyPP amir$ 
Amirs-MacBook-Air-2:ShopifyPP amir$ 
Amirs-MacBook-Air-2:ShopifyPP amir$ 
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS read 2
Record [id = 2, value = Shopify]
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS read 1
Record [id = 1, value = Matt]
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS read 0
Record [id = 0, value = Amir]
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS read -1
invalid id
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS read 412
invalid id
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS read 9
invalid id
Amirs-MacBook-Air-2:ShopifyPP amir$ java DBMS list
Record [id = 0, value = Amir]
Record [id = 1, value = Matt]
Record [id = 2, value = Shopify]
```





