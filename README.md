# avro-kafka-spring

# Spring Boot Kafka Project

This project demonstrates a simple Spring Boot application with Kafka integration. It includes producer and consumer configurations to send and receive messages using Kafka. The project is designed to run within a Docker Compose environment, which provides the necessary Kafka server and ZooKeeper instances.

## Prerequisites

- Docker: Ensure that Docker is installed on your machine. You can download it from the official Docker website: [https://www.docker.com](https://www.docker.com).

## Getting Started

Follow the steps below to get the project up and running:

1.Clone the repository:

   git clone https://github.com/riyaknol11/avro-kafka-spring.git
   
2. Navigate to the project directory.

3. Start the Docker Compose environment by executing the below command in the terminal:
         docker-compose up -d
This command will spin up the Kafka server and ZooKeeper instances required for running the application.

4. Verify that the Kafka server and ZooKeeper are running by running the command : docker-compose ps 

Configuring the Producer and Consumer
The project comes with default configurations for the Kafka producer and consumer. Since, it is a multi-module project. Both producers and consumers have been configured individually. However, you can modify these configurations to suit your needs. The relevant files can be found in the src/main/resources directory for both consumer and producer:

application.yml: Contains the application-level configuration properties, including Kafka server details and topic names, groupId.

> Apart from these configurations, following are the config files for both consumer and producer.
coffee-order-service/config/KafkaConfig: Configures the Kafka producer properties such as bootstrap servers, key and value serializers, etc.
coffee-order-consumer/config/KafkaConfig: Configures the Kafka consumer properties such as bootstrap servers, group ID, key and value deserializers, etc.


5. Running the Application
To run the Spring Boot Kafka application, follow these steps:

a. Start the Docker Compose environment if it's not already running:
docker-compose up -d
b. Run the Spring Boot application of Producer and Consumer both, Producer will run on default port: 8080 and Consumer service will run on port 8083 as configured in application.properties.
c. After running the applications, hit the endpoint http://localhost:8080/avro/api/coffee_order from postman.
d. Providing the data that will be published to the topic by the producer. Use the follwing fields schema while hitting the endpoint.

{
  "id": 1,
  "name": "John",
  "nickName": "Johnny",
  "store": {
    "id": 1,
    "address": {
      "city": "Manhattan",
      "street": "123 Main St",
      "zip": 10001
    }
  },
  "orderLineItems": [
    {
      "name": "Latte",
      "quantity": 2,
      "size": "MEDIUM"
    },
    {
      "name": "Cappuccino",
      "quantity": 1,
      "size": "SMALL"
    }
  ]
}



e.Cleaning Up
To stop and remove the Docker Compose environment and containers, run the following command in the project directory:
      docker-compose down


