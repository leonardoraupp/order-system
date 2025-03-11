# Project Order System ![Documentation Status](https://img.shields.io/badge/docs-in%20progress-yellow)


## Running the project
- First, necessary to up the containers for the database, RabbitMQ and other dependencies.
  
```bash
cd order_system project folder
docker-compose up -d
```
- Run the application order-system

  ## Testing

  ### Register user:

  /auth/register

  User data:

  ```json
  {
    "username": "Leonardo",
    "password": "leonardo123"
  }
  ```

  ### Authenticate user:

  /auth/authenticate

  User data:

    ```json
    {
      "username": "Leonardo",
      "password": "leonardo123"
    }
    ```
  
  Get the token from the response and set on Authorization  header as Bearer Token type for the next requests on other endpoints.

    ### Create user role:

  /roles

  User role data:
  
  ```json
  {
    "name": "ADMIN"
  }
  ```

    ### Assign user role to user:

  /users/{userID}/roles

  User role data:

    ```json
    {
      "id": 1,
      "name": "ADMIN"
    }
    ```

## JWT Authentication and Authorization
### Controllers

- /auth/register: Register an user.
- /auth/authenticate: Authenticate an valid user and provides a new JWT token.
- /users: Actions on user entity.
- /roles: Actions on user roles entity.

Any other endpoint access is necessary be authenticated and with the necessary authorization.

## RabbitMQ Messaging 
### Queues

order-queue: Main queue where messages are published.

order-dlq: Dead-letter queue where messages are sent after the maximum number of retries.

### Producer

Publishes messages to the order-queue.

### Consumer

Consumes messages from the order-queue.

If the message is invalid (e.g., amount < 0), it throws an exception to trigger retries.

After 3 retries, the message is sent to the order-dlq by the RepublishMessageRecoverer.

### Retry Configuration

Configured in RabbitMQConfig using RetryOperationsInterceptor and RepublishMessageRecoverer.

## Benefits of This Approach

1. Simplicity:

- Only two queues are needed (order-queue and order-dlq).
- The retry flow is centralized in the Spring AMQP configuration.

2. Ease of Maintenance:

- No need to create additional consumers for the DLQ.
- Messages in the DLQ can be inspected and processed manually if needed.

3. Scalability:

- Spring AMQP handles retries automatically without overloading the consumer code.

2. Best Practices Compliance:

- Using a DLQ for failed messages is a common practice in messaging systems.
- The RepublishMessageRecoverer ensures messages are reliably sent to the DLQ.
