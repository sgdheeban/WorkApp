# WorkApp Server

This is the main server module, which hosts the backend server logic for WorkApp project. This module contains the following : 

## Sub-Components

  1. Common Logger [Common Resources Package]
  2. File and Command Line based Configuration Reader [Data Access Package]
  3. Parser-Serializer-Deserializer for JSON, XML, HTML, DOT, MySQL, SQLLiTe, CQL [Common Resources Package]
  4. SQL Query Builder [Data Access Package]
  5. Entity Framework  [Data Access Package]  
  6. Object Allocation Tracker [Common Resources Package]
  7. Code Instrumentor [Common Resources Package]
  8. PerfMetric Counter [Common Resources Package]  
  9. Java MultiThreading Pipeline [Common Resources Package]
  10. Database Connection Manager - For SQLite, MySQL, PostgreSQL, Cassandra, Mongo [Data Access Package]
  11. DataService Connection Manager - for usefuk toools like Kafka, Zookeeper [Data Access Package]  
  12. Caching Layer - for Caches like Guava, Redis, Memcached [Common Resources Package]  
  13. REST Service Layer using Embedded HTTP Jetty Server on port 8080 [Common Resources / Service Package]
  14. THRIFT Message Layer using Embedded THRIFT server on port 9888 [Common Resources / Service Package]
  15. Distributed Coordination Layer [Common Resources / Service Package]
  16. Queuing Layer [Common Resources / Service Package]
  16. Model Business Objects [Model Package]
  17. Actual Business Logic using the underlying components [Model Package / Service Package]
  
  
  
