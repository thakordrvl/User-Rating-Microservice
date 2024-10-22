# Microservices Project

This project demonstrates the implementation of a microservices architecture, focusing on building a modular and scalable system. The project consists of several services, each responsible for a specific functionality, with communication between them managed via a service registry and API gateway.

## Project Overview

The project is structured around multiple microservices, each handling different components such as user management, hotel services, rating systems, and more. The microservices are designed to interact with each other through REST APIs while maintaining loose coupling.

### Key Features

- **User Microservice**: Handles user registration, authentication, and user profile management. All data related to users is stored in a database.
- **Hotel Microservice**: Manages information about hotels, including adding, updating, and retrieving hotel details. It uses PostgreSQL as the database.
- **Rating Microservice**: This service allows users to rate hotels and stores the ratings using MongoDB. It is designed to fetch ratings dynamically based on the hotel and user IDs.
- **Service Discovery**: Uses Eureka Server to manage service registration and discovery, enabling microservices to locate and communicate with each other dynamically.
- **API Gateway**: Provides a single entry point to all services. It routes requests to appropriate services, manages load balancing, and offers enhanced security.
- **Configuration Server**: Centralized configuration management using a config server that retrieves settings from a version control repository like Git.
- **Fault Tolerance**: Implements Resilience4J for handling faults and ensuring system resilience. Features such as circuit breakers and retries are included to handle service failures gracefully.
- **Security**: Integrates OAuth2.0 and JWT-based authentication to secure the microservices, ensuring that only authorized users can access specific resources.

### Technologies Used

- **Java 17**: Core programming language for developing the microservices.
- **Spring Boot**: Framework for building standalone microservices.
- **PostgreSQL**: Relational database used by the hotel service.
- **MongoDB**: NoSQL database used by the rating service.
- **Eureka**: Service discovery mechanism for registering and locating microservices.
- **Spring Cloud Gateway**: API Gateway for routing requests and securing the services.
- **Resilience4J**: Fault tolerance library used for implementing circuit breakers and retries.
- **Spring Security & OAuth2**: For securing API endpoints with token-based authentication.
- **Feign Client**: Simplifies inter-service communication by abstracting HTTP calls.
  
### Microservices

1. **User Service**: 
   - Register and manage users.
   - Communicates with rating service to fetch user-specific ratings.
   
2. **Hotel Service**:
   - Manages hotel information.
   - Interacts with the rating service to aggregate ratings for hotels.
   
3. **Rating Service**:
   - Allows users to rate hotels.
   - Fetches data dynamically based on the user and hotel information.

4. **Service Registry**:
   - Eureka server that enables service discovery for dynamic service interaction.

5. **API Gateway**:
   - Routes incoming requests to respective microservices.
   - Simplifies client interaction by providing a unified interface for multiple services.

6. **Config Server**:
   - Centralized configuration management system to handle microservice configurations.

### Setup and Running the Project

1. **Build the project**:
   Make sure you have Maven or Gradle installed. Run:
   ```bash
   mvn clean install
   ```

2. **Run the individual services**:
   You can start each microservice individually from its respective directory or set up Docker containers for better orchestration.

   Example:
   ```bash
   cd user-service
   mvn spring-boot:run
   ```

3. **Access the API Gateway**:
   Once all services are up, access the API Gateway at the port number present in config file of API Gateway:
   ```
   http://localhost:xxx
   ```

4. **Interact with services**:
   Use Postman or curl to test the APIs for creating users, managing hotels, and submitting ratings.

### Future Enhancements

- **Rate Limiting**: Add rate limiting to prevent overloading of services.
- **Distributed Tracing**: Implement tracing for better observability of requests flowing through microservices.
- **Monitoring and Alerts**: Set up monitoring for microservices using tools like Prometheus and Grafana.

### Conclusion
This project showcases the fundamentals of microservices architecture with an emphasis on scalability, modularity, and fault tolerance. Each service operates independently, ensuring easy management and potential for future expansion. The use of Spring Cloud technologies makes the services robust and ready for production environments.

