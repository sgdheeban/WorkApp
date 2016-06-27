# WorkApp ORM Engine

Our ORM layer internally uses a customized Persist Engine, which is a simple, light-weight Java-based ORM/DAO tool. 

Persist Engine works around a @java.sql.Connection@ object. This means that it does not care about customer query
languages (it uses plain SQL with placeholders, as @PreparedStatement@ objects use), connection pool handling,
transaction handling (for the most part), and so on. This also means it is very flexible, and can be integrated with any
code that depends on JDBC (including code that already use another ORM/DAO tool).

Persist does not require explicit mappings from POJOs to database tables. As long as there is some sort of naming
conventions that relate database names with POJO names, Persist will require virtually no mappings. It can, however, be
instructed to map Java classes and fields to database tables and columns using annotations.

Persist requires no singletons, no ThreadLocal's, no global objects and no configuration files. It has
no external dependencies (although it will use Log4j, if available). It has a very small, robust and straightforward
codebase, which can be easily debugged in case you face an unforeseen problem. It is actively tested (with a high code
coverage) for MySQL, PostgreSQL, H2/HSQLDB, Derby, Oracle, DB2 and MS SQL Server. It imposes a very small overhead to
comparable plain JDBC operations, making it very attractive for high performance and/or low footprint applications.

Persist is distributed under a BSD license.

## Quickstart

A Persist engine only requires a @java.sql.Connection@ to be created:

<pre>
 Persist persist = new Persist(connection);
</pre>

Persist supports several different mapping strategies:

### POJOs mapped to tables

By default, if no annotations specify a given class should not be mapped to a table, Persist will try to find a
table that matches that class and create a mapping between fields and columns.

<pre>
 // inserts a new customer (the class _Customer_ is mapped to the table _customer_ automatically)
 persist.insert(customer);

 // reads a customer by its primary key
 Customer c = persist.readByPrimaryKey(Customer.class, 42);

 // retrieves customers using a custom query (note the usage of varargs)
 List<Customer> list = persist.readList(Customer.class, "select * from customer where id > ?", 10);

 // fetch all customers and assign the ResultSet to an Iterator
 Iterator allCustomersIterator = persist.readIterator(Customer.class, "select * from customer");
</pre>

### POJOs not mapped to tables

If a class is annotated with @@NoTable@, Persist will not try to map it to a table, and the class will
only be able to hold data produced by queries.

<pre>
 @NoTable
 class QueryData {
    private int count;
    private String concatName;
   
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
   
    public String getConcatName() { return concatName; }
    public void setConcatName(String concatName) { this.concatName = concatName; }
 }

 QueryData qd1 = persist.read(QueryData.class, "select 1 as count, 'hello' as concat_name from dual");
 QueryData qd2 = persist.read(QueryData.class, "select 2 as counts, null as concatnames from dual");
</pre>

### java.util.Map's

@Map@ instances can be used to hold data from queries. Persist will convert values returned from the query to Java
types. Keys in the table are the names of the columns returned in lower case.

<pre>
 // fetch a customer using a custom query and return the result as a map
 Map<String,Object> customerMap = persist.readMap("select * from customer where id=?", 10);

 // fetch all customers and result the results as Map instances in a List 
 List<Map<String,Object>> customerMapList = persist.readMapList("select * from customer");

 // fetch all customers and assign the ResultSet to an Iterator which maps rows to Map instances
 Iterator allCustomersIterator = persist.readMapIterator("select * from customer");
</pre>

### Java primitive types

If a query returns a single column, Persist can map data directly into primitive types (either single values or lists):

<pre>
 // return customer name as String
 String name = persist.read(String.class, "select name from customer where id=?", 55);

 // fetch all customer id's as a list of integers
 List<Integer> ids = persist.readList(int.class, "select id from customer");
</pre>

### Custom queries with no returning data

Arbitrary queries that return no data can be easily executed.

<pre>
 // execute arbitrary SQL with parameters
 persist.executeUpdate("delete from customer where id in (?,?)", 10, 20);
</pre>

For the POJO mapping strategies, persist can map names either using explicit annotations or through a _name
guesser_ which translates class and field names to table and column names. A name guesser is a class that implements an
interface with a single method, and provide a generic, uniform way of translating names from the database schema to the
Java name conventions.

That's almost everthing you need to know before using Persist! To get a better understanding on its internals,
please consult the following sections.

### Creating Persist instances

Persist only requires a @java.sql.Connection@ object to be created:

<pre>
 Persist persist = new Persist(connection);
</pre>

### Caches

Internally, Persist will maintain a cache for all mapped objects it interacts with. Caches exist on a classloader
basis. If an application has to deal with connections with different databases, different mapping caches must be used.

To specify different caches, use a _cache name_ in the constructor:

<pre>
 // Create a persist instance for MySQL using the default cache
 Persist persistMysql = new Persist(connectionMysql);

 // Create a persist instance for Oracle using the "oracle" cache name
 Persist persistOracle = new Persist("oracle", connectionOracle);
</pre>

### Logging

If Persist can find Log4J in the classpath, it will use it. The following channels are used, all in @debug@
mode:

* @orm.engine@
* @orm.parameters@
* @orm.results@
* @orm.profiling@

## Annotations

There are a few annotations that control the mapping behavior from classes to database tables:

* @@Table@ can be associated with a class and specify the name of the table that class is mapped to
* @@Column@ can be associated with a setter or getter of a field and specify the name of the column to be
associated with that field, and/or specify if the field is auto-incremented by the database upon insertion.
* @@NoTable@ can be associated with a class to specify the class should not be mapped to a table in the
database. Classes annotated with @@NoTable@ can only be used to hold data from queries.
* @@NoColumn@ can be associated with a setter or getter of a field and specify it should not be mapped to
a column in the database (by default, Persist attempts to map all fields of a given class to columns in the table
associated with their class).

## Mapping POJOs to tables

By default, if Persist is given a class, it will try to map it to a database table. To do so, it relies either on
explicit annotations (such as @@Table@ and @@Column@) or _name guessers_ which are configurable and
determine a global translation mechanism between class and field names to table and column names.

Consider the following table definition and its associated bean

<pre>
 create table customer (
   id int auto_increment,
   name varchar(255),
   primary key (id)
 )
</pre>

<pre>
 class Customer {
    private int id;
    private String name;
   
    @Column(autoIncrement=true)
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
   
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
 }
</pre>

class @Customer@ and its fields don't specify annotations to explicitly define which tables and columns
should be used. Therefore, when Persist tries to map this class, it will use a _name guesser_. Name guessers are
responsible for programatically converting class and field names to table and column names, using whichever convention
is in place.

Since no _name guesser_ was specified, the @DefaultNameGuesser@ will be used. The @DefaultNameGuesser@
converts class and field names in the form @CompoundName@ to this list of guessed names: @[compound_name,
compound_names, compoundname, compoundnames]@

While performing the automatic mapping for the @Customer@ class above, Persist would try to find any of
those tables in the database: @[customer, customers]@. Since the table @customer@ exists, it picks it for
the mapping and start mapping fields from @Customer@ to columns in @customer@ using the same approach.

During the process of mapping columns to fields, Persist stores information about which columns are primary keys
and which ones are auto-incremented upon insertion (this must be specified using @@Column(autoIncrement=true)@
since there's no deterministic way of doing this automatically).

This mapping process only happens once (per class per classloader). Persist stores mappings for each class it
interacts with in an internal cache

After a mapping is created, Persist can perform CRUD operations directly on instances of the @Customer@
POJO. To illustrate the whole process, consider the following code

<pre>
 // create a new customer instance
 Customer customer = new Customer();
 customer.setName("a new customer");

 // fetch a customer using its primary key
 Customer c = persist.readByPrimaryKey(Customer.class, 10);
</pre>

The moment persist has contact with @Customer@, it will build a mapping automatically and cache it. With
the mapping, it will know that the @Customer@ class is mapped to the @customer@ table, and that @id@
is the primary key in that table. With that information, Persist can issue a @select@ SQL statement querying for
all columns in @customer@ having the specified primary key (@id@).

Other CRUD operations can be used directly as well

<pre>
 // insert
 persist.insert(customer);

 // update
 persist.update(customer);

 // delete
 persist.delete(customer);
</pre>

*Important note:* Persist can only perform +@readByPrimaryKey@+, +@update@+ and +@delete@+
operations for classes mapped to tables that +have primary keys+. @insert@ and all the @read@
operations can work on any POJO mapped on any table.

Persist supports several different ways of reading data from mapped tables:

<pre>
 // fetch a single customer using a custom query
 Customer customer = persist.read(Customer.class, "select * from customer where id = 10");

 // fetch all customers
 List<Customer> allCustomersList = persist.readList(Customer.class);

 // fetch a set of customers using a custom query
 List<Customer> customersList = persist.readList(Customer.class, "select * from customer where id < ?", 100);

 // fetch all customers using a custom query and assign the ResultSet to an Iterator which maps rows to Customer instances
 Iterator allCustomersIterator = persist.readIterator(Customer.class, "select * from customer where id in (?,?)", 10, 20);
</pre>

### Types and conversions

Persist will respect the Java types of the fields on a given POJO as much as it can while retrieving data from
ResultSet's. Furthermore, Persist can perform type conversions to/from query parameters and ResultSet columns.

The following tables depict the ResultSet.get and PreparedStatement.set methods used for each Java type:

|_. Java type |_. ResultSet.get method |_. PreparedStatement.set method |
| Boolean/boolean | getBoolean | setBoolean |
| Byte/byte | getByte | setByte |
| Short/short | getShort | setShort |
| Integer/int | getInt | setInt |
| Long/long | getLong | setLong |
| Float/float | getFloat | setFloat |
| Double/double | getDouble | setDouble |
| Character/char | getString | setString |
| Character[]/char[] | getString | setString |
| Byte[]/byte[] | getBytes | setBytes |
| String | setString | setString |
| java.math.BigDecimal | getBigDecimal | setBigDecimal |
| java.io.Reader | getCharacterStream | setCharacterStream |
| java.io.InputStream | getBinaryStream | setBinaryStream |
| java.util.Date | getTimestamp | setTimestamp |
| java.sql.Date | getDate | setDate |
| java.sql.Time | getTime | setTime |
| java.sql.Timestamp | getTimestamp | setTimestamp |
| java.sql.Clob | getClob | setClob |
| java.sql.Blob | getBlob | setBlob |

## POJOs not mapped to tables (@NoTable)

POJOs that are annotated with @NoTable@ can only be used to hold data from queries. Mapping for classes
annotated with @NoTable@ is performed using these rules:

* Class names won't affect the mapping
* If a field contains a @@Column(name="...")@ annotation, then only the specified column name will be
used for that field
* Otherwise, all column names returned by the _name guesser_ will be associated to the field
* If more than one field have conflicting table names (either from @@Column@ annotations or from guessed
names), Persist will throw an exception while trying to use the class

To illustrate how this works, consider the following class:

<pre>
 class QueryData {
    private int count;
    private String concatName;
   
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
   
    public String getConcatName() { return concatName; }
    public void setConcatName(String concatName) { this.concatName = concatName; }
 }
</pre>

Persist would create the following mapping for this class:

|_. Column names |_. Field name |
| count, counts | count |
| concat_name, concat_names, concatname, concatnames | concatName |

Some examples of how this would work using dummy queries:

<pre>
 QueryData qd1 = persist.read(QueryData.class, "select 1 as count, 'hello' as concat_name from dual");
 QueryData qd2 = persist.read(QueryData.class, "select 2 as counts, null as concatnames from dual");
</pre>

Type conversions are performed using the same conversion table as POJOs mapped to tables use.

## java.util.Map's

Query results can be mapped directly to @java.util.Map@ instances, using the readMap-prefixed methods.
Keys in the map are the names of the columns in lower case, and values are fetched from the ResultSet.

<pre>
 // fetch a customer using a custom query and return the result as a map
 Map<String,Object> customerMap = persist.readMap("select * from customer where id=?", 10);

 // fetch all customers and result the results as Map instances in a List 
 List<Map<String,Object>> customerMapList = persist.readMapList("select * from customer");

 // fetch all customers and assign the ResultSet to an Iterator which maps rows to Map instances
 Iterator allCustomersIterator = persist.readMapIterator("select * from customer");
</pre>

Values are retrieved from the ResultSet according with their SQL types (as defined in java.sql.Types), so that
for each SQL type Persist will request the value according with a pre-defined Java type, as specified in the following
table:

|_. SQL type |_. ResultSet.get method |
| ARRAY | getArray |
| BIGINT | getLong |
| BIT | getBoolean |
| BLOB | getBytes |
| BOOLEAN | getBoolean |
| CHAR | getString |
| CLOB | getString |
| DATALINK | getBinaryStream |
| DATE | getDate |
| DECIMAL | getBigDecimal |
| DOUBLE | getDouble |
| FLOAT | getFloat |
| INTEGER | getInt |
| JAVA_OBJECT | getObject |
| LONGVARBINARY | getBytes |
| LONGVARCHAR | getString |
| NULL | getNull |
| NCHAR | getString |
| NUMERIC | getBigDecimal |
| OTHER | getObject |
| REAL | getDouble |
| REF | getRef |
| SMALLINT | getInt |
| TIME | getTime |
| TIMESTAMP | getTimestamp |
| TINYINT | getInt |
| VARBINARY | getBytes |
| VARCHAR | getString |
| 100 (Oracle specific) | getFloat |
| 101 (Oracle specific) | getDouble |

## Primitive types

Persist can map query results having a single column directly to primitive Java types (such as int, Double,
String, etc.), either as single values or lists.

<pre>
 // return customer name as String
 String name = persist.read(String.class, "select name from customer where id=?", 55);

 // fetch all customer id's as a list of integers
 List<Integer> ids = persist.readList(int.class, "select id from customer");
</pre>

Type conversions are performed using the same conversion table as POJOs mapped to tables use.