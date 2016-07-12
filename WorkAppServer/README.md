# WorkApp Server

This is the main server module, which hosts the backend server logic for WorkApp project. 

## Sub-Components

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
  11. Supports Ehcache, Guava Cache, ActiveMQ, RocksDB, Thrift Integration [Maven Jars Included]
  12. Algorithm Packages - Constantly updated as a Learning Exercise [Common Resources Packages]
  13. Customize Business Objects using underlying components - Contains sample symbol table API [Model Package]
