# WorkApp Server

This is the main server module, which hosts the backend server logic for WorkApp project. 

## Sub-Components

### Core Components

  1. Common Logger [Common Resources Package]
  2. File and Command Line based Configuration Reader [Data Access Package]
  3. Deserializer between POJO and JSON [Common Resources Package]
  4. SQL Query Builder [Data Access Package]
  5. Entity-ORM Framework  [Data Access Package]  
  6. Object Allocation Instrumentor-Tracker [Common Resources Package]
  7. PerfMetric Counter-Data Manager [Common Resources Package]  
  8. Java MultiThreading Pipeline - Queuing and Batching Layer [Common Resources Package]
  9. Database Connection Manager for MySQL [Data Access Package]
  10. REST Service Layer using Embedded Jetty-Jersey Server on port 8080 [Common Resources / Service Package]

### Business Logic Components

  1. Model Business Objects & Business Logic using the underlying components [Model Package]

### Additional Components for Scale-Out / Scale-Up Performance Improvements

  1. Generic Timed Cache Manager for in-memory caches like Ehcache, Guava Cache [Common Resources Package]
  2. Service Consumers for distributed services like Kafka, Zookeeper [Data Access Resources Package]  
  3. Caching Consumers for distributed caches like Redis, Memcached [Common Resources Package]  
  4. THRIFT Message Layer using Embedded THRIFT server on port 9888 [Service Package] 
  5. Distributed Coordination Layer [Common Resources Package]  
