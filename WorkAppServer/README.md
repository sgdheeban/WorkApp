# WorkApp Server

This is the main server module, which hosts the backend server logic for WorkApp project. This module contains the following : 

## Sub-Components

  1. Common Logger [Common Resources Package]
  2. File and Command Line based Configuration Manager [Common Resources Package]
  3. Serializer-Deserializer for JSON, XML [Common Resources Package]
  4. REST Service Layer using Embedded HTTP Jetty Server on port 8080 [Common Resources / Service Package]
  5. THRIFT Message Layer using Embedded THRIFT server on port 9888 [Common Resources / Service Package]
  6. Caching Layer - for Caches like Guava, Redis, Memcached [Common Resources Package]
  7. Object Allocation Tracker [Common Resources Package]
  8. Code Instrumentor [Common Resources Package]
  9. PerfMetric Counter [Common Resources Package] 
  10. Model Business Objects [Model Package] 
  11. Database Connection Manager - For SQLite, MySQL, PostgreSQL, Cassandra, Mongo [Data Access Package]
  12. DataService Connection Manager - for usefuk toools like Kafka, Zookeeper [Data Access Package]
  13. SQL Query Builder [Data Access Package]
  14. Entity Framework  [Data Access Package]
  
