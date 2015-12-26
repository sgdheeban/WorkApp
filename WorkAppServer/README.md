# WorkApp Server

This is the main server module, which hosts the backend server logic for WorkApp project. This module contains the following : 

## Sub-Components

  1. Common Logger [Common Resources Package]
  2. File and Command Line based Configuration Reader [Data Access Package]
  3. Parser-Serializer-Deserializer between POJO and JSON, XML, HTML, DOT, MySQL, SQLLiTe, CQL [Common Resources Package]
  4. SQL Query Builder [Data Access Package]
  5. Entity-ORM Framework  [Data Access Package]  
  6. Object Allocation Instrumentor-Tracker [Common Resources Package]
  7. PerfMetric Counter-Data Manager [Common Resources Package]  
  8. Java MultiThreading Pipeline - Queuing and Batching Layer [Common Resources Package]
  9. Database Connection Manager for SQLite, MySQL, PostgreSQL, Cassandra, Mongo [Data Access Package]
  10. Service Consumers for useful services like Kafka, Zookeeper [Data Access Package]  
  11. Caching Consumers for caches like Guava, Redis, Memcached [Common Resources Package]  
  12. REST Service Layer using Embedded HTTP Jetty Server on port 8080 [Common Resources / Service Package]
  13. THRIFT Message Layer using Embedded THRIFT server on port 9888 [Common Resources / Service Package] 
  14. Distributed Coordination Layer [Common Resources Package]
  15. Model Business Objects & Business Logic using the underlying components [Model Package]
  
  
  
