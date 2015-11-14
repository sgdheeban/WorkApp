# WorkApp Server

This is the main server module, which hosts the backend server logic for WorkApp project. This module contains the following : 

## Sub-Components

  1. Common Logger [Common Resources Package]
  2. File and Command Line based Configuration Manager [Common Resources Package]
  3. REST Service Layer using Embedded HTTP Jetty Server on port 8080 [Common Resources / Service Package]
  4. THRIFT Message Layer using Embedded THRIFT server on port 9888 [Common Resources / Service Package]
  5. Caching Layer - for In-Memory Cache like Guava and External Caches like Redis, Memcached [Common Resources Package]
  6. Object Allocation Tracker [Common Resources Package]
  7. Code Instrumentor [Common Resources Package]
  8. PerfMetric Counter [Common Resources Package] 
  9. Model Objects with Serializer-Deserializer between JSON-Query [Model Package] 
  10. Database Connection Manager - For SQLite, MySQL, PostgreSQL, Cassandra, Mongo [Data Access Package]
  11. DataService Connection Manager - for usefuk toools like Kafka, Zookeeper [Data Access Package]
  12. SQL Query Builder [Data Access Package]
  13. Entity Framework  [Data Access Package]
  
