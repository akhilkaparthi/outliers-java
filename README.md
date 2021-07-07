#Asset Outlier Restful Service
An implementation of Restful Service using Spring Boot, Java 8.

##Implementation
1) Bootstrap a project with Spring Initializer
2) Implement a Business Service of API - Asset Service
3) Implement the API - using AssetController. First I will implement the GET Method and then POST methods.
4) Unit Test the API

### Bootstrap REST Service Application with Spring Initializer
1) Launch Spring Initialzr : choose the following
 choose group, artifact and dependencies like Web, Actuator, Devtools

2) Click on Generate Project
3) Import to IntelliJ

### Implementing the Business Service for the Application
ArrayList is used as in-memory data.

An asset consists of various details like Age, Uptime and Number of Past failures.

An Asset has id, age, uptime and number of failures.

An AssetService exposing methods to
1. public List<Asset> getAssets() - Retrieves details of all Assets.
2. public Asset getAsset(Asset asset) - Retrieves a specific asset  details
3. public Asset addAsset(Asset asset) - Add an asset
4. public List<Asset> findOutliers() - Retrieves the rough records in the Assets List.

### Model and Service implementation
Model---> Asset.java, 
Service----> AssetService.java


## Following properties are used for metrics and monitoring
prometheus and actuator

## Used for CI/CD
Distributed Management
SEM section




 



