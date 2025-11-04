🚀 Quantum Logistics: AI-Driven Microservices PlatformQuantum Logistics is a complete, end-to-end, event-driven microservices system built to simulate a modern logistics and shipping network. This project demonstrates a cloud-native architecture using Java, Spring Boot, Kafka, and Docker, and serves as a blueprint for a scalable, resilient, and intelligent enterprise system.This project was built to demonstrate core principles of distributed systems, including:Service Discovery (with Eureka)Centralized Entry (with an API Gateway)Event-Driven Architecture (with Kafka)Asynchronous Processing (fan-out pattern)AI Integration (for intelligent routing)🏗️ System ArchitectureThis system uses a "choreography" pattern, where services communicate by producing and consuming events via a central Kafka message bus, without needing to know about each other.graph TD
    subgraph "Local Infrastructure (Docker)"
        direction LR
        KAFKA_BUS[<img src='[https://static-00.iconduck.com/assets.00/apache-kafka-icon-2048x2022-d1lt50nd.png](https://static-00.iconduck.com/assets.00/apache-kafka-icon-2048x2022-d1lt50nd.png)' width='50' /><br/>Kafka Bus]
        POSTGRES_DB[<img src='[https://static-00.iconduck.com/assets.00/postgresql-icon-1987x2048-v22kmfxi.png](https://static-00.iconduck.com/assets.00/postgresql-icon-1987x2048-v22kmfxi.png)' width='50' /><br/>PostgreSQL DB]
    end

    subgraph "Application Services (Java/Spring)"
        direction LR
        REGISTRY[<img src='[https://spring.io/images/projects/spring-cloud/icon-spring-cloud-light.svg](https://spring.io/images/projects/spring-cloud/icon-spring-cloud-light.svg)' width='50' /><br/>Service Registry (8761)]
        GATEWAY[<img src='[https://spring.io/images/projects/spring-cloud/icon-spring-cloud-gateway-light.svg](https://spring.io/images/projects/spring-cloud/icon-spring-cloud-gateway-light.svg)' width='50' /><br/>API Gateway (8085)]
        SERVICE_A[<img src='[https://spring.io/images/projects/spring-boot/icon-spring-boot-light.svg](https://spring.io/images/projects/spring-boot/icon-spring-boot-light.svg)' width='50' /><br/>Shipment Service (8080)]
        SERVICE_B[<img src='[https://spring.io/images/projects/spring-boot/icon-spring-boot-light.svg](https://spring.io/images/projects/spring-boot/icon-spring-boot-light.svg)' width='50' /><br/>Analytics Service (8081)]
        SERVICE_C[<img src='[https://spring.io/images/projects/spring-boot/icon-spring-boot-light.svg](https://spring.io/images/projects/spring-boot/icon-spring-boot-light.svg)' width='50' /><br/>Logistics AI Service (8082)]
    end

    CLIENT[<img src='[https://static-00.iconduck.com/assets.00/postman-icon-497x512-beb7n22c.png](https://static-00.iconduck.com/assets.00/postman-icon-497x512-beb7n22c.png)' width='50' /><br/>Postman Client] --> GATEWAY
    
    GATEWAY --> REGISTRY
    SERVICE_A --> REGISTRY
    SERVICE_C --> REGISTRY

    GATEWAY -- "Routes to /shipment-service/**" --> SERVICE_A
    SERVICE_A -- "Produces 'shipments-created' Event" --> KAFKA_BUS
    KAFKA_BUS -- "Consumes (Group 1)" --> SERVICE_B
    KAFKA_BUS -- "Consumes (Group 2)" --> SERVICE_C
    SERVICE_B --> POSTGRES_DB

    style KAFKA_BUS fill:#fff,stroke-width:0px
    style POSTGRES_DB fill:#fff,stroke-width:0px
    style REGISTRY fill:#fff,stroke-width:0px
    style GATEWAY fill:#fff,stroke-width:0px
    style SERVICE_A fill:#fff,stroke-width:0px
    style SERVICE_B fill:#fff,stroke-width:0px
    style SERVICE_C fill:#fff,stroke-width:0px
    style CLIENT fill:#fff,stroke-width:0px
Core Servicesservice-registry (Eureka Server):The "phone book" for the system. All other microservices register themselves here, allowing them to find each other by name (e.g., shipment-service) instead of hard-coded ports.api-gateway (Spring Cloud Gateway):The single "front door" for all public traffic. It handles request routing, security (future), and load balancing. It dynamically routes traffic (e.g., /shipment-service/...) by looking up the service's address in the service-registry.shipment-service (Spring Boot):The "worker" service. It exposes the public POST /api/shipments endpoint.Its only job is to validate the request and produce a shipments-created event to the Kafka bus.analytics-service (Spring Boot / Kafka Consumer):A "listener" service. It subscribes to the shipments-created topic.When it "hears" a message, it consumes it, converts it to a database entity, and saves it to the PostgreSQL database.logistics-ai-service (Spring Boot / Spring AI):A second "listener" that demonstrates a fan-out pattern (one event, multiple actions).It also subscribes to the shipments-created topic using a different group-id.It takes the origin and destination, forms a prompt, and calls the OpenAI API to calculate an optimal shipping route, logging the result.🛠️ Technology StackCategoryTechnologyPurposeBackendJava 17Core application languageFrameworkSpring Boot 3Building all 5 microservicesService MeshSpring Cloud GatewayAPI Gateway (Front Door)Service MeshSpring Cloud EurekaService Registry (Phone Book)Events / MessagingApache KafkaAsynchronous event busDatabasePostgreSQLStoring shipment analyticsAISpring AI (OpenAI)Intelligent route calculationDevOpsDocker / Docker ComposeContainerizing & running infrastructureBuildApache MavenProject build and dependency managementTestingPostmanAPI request testing🏁 How to Run This Project1. PrerequisitesJava 17 (JDK)Apache Maven 3.9+Docker DesktopAn OpenAI API Key (for the AI service)VS Code (with the Extension Pack for Java)Postman2. ConfigurationBefore launching, you must provide your OpenAI API key.Go to services/logistics-ai-service/src/main/resources/application.properties.Replace the placeholder YOUR_API_KEY_HERE with your actual OpenAI key:spring.ai.openai.api-key=sk-YourActualKeyGoesHere...
3. Local Launch Sequence (The "Idiot-Free" Order)This system has 6 components that must be started in order. Open each of the 5 Java services in its own VS Code window.Start the Infrastructure (Docker):Open a terminal in the project's root quantum-logistics folder.cd dockerdocker-compose up -d(This starts Kafka, Zookeeper, Postgres, and the Kafka-UI).Start the "Phone Book" (Service Registry):Open the service-registry project.Run ServiceRegistryApplication.java.✅ Verify: Go to http://localhost:8761/. You should see the Eureka dashboard.Start the "Front Door" (API Gateway):Open the api-gateway project.Run ApiGatewayApplication.java.✅ Verify: Refresh http://localhost:8761/. You should see API-GATEWAY appear in the list.Start the "Listeners" (Analytics & AI):Open the analytics-service project and run AnalyticsServiceApplication.java.Open the logistics-ai-service project and run LogisticsAiServiceApplication.java.✅ Verify: Refresh http://localhost:8761/. You should now see LOGISTICS-AI-SERVICE appear.Start the "Worker" (Shipment Service):Open the shipment-service project.Run ShipmentServiceApplication.java.✅ Verify: Refresh http://localhost:8761/. All 3 services (API-GATEWAY, LOGISTICS-AI-SERVICE, SHIPMENT-SERVICE) should be UP.The entire system is now online.🚀 Testing the Full PipelineUse Postman to send a POST request to the API Gateway (the "Front Door"), not the service itself.URL: POST http://localhost:8085/shipment-service/api/shipmentsBody (JSON):{
    "origin": "Oracle HQ, Austin, TX",
    "destination": "Oracle Office, Nashville, TN",
    "customerId": "CUST-ORACLE-EVENT"
}
Expected Fan-Out ResultBy sending that one request, you will trigger three simultaneous actions:In Postman: You will get a 200 OK response immediately.In the analytics-service Terminal: The log Received shipment... CUST-ORACLE-EVENT will appear as it saves the data to Postgres.In the logistics-ai-service Terminal: The log AI SERVICE RECEIVED: CUST-ORACLE-EVENT will appear, followed by an AI RESPONSE: with the calculated optimal route.