![logo.png](logo.png)
# Task Management App

Manage tasks and projects effectively through a web-based application. This system enables task creation, assignment, progress tracking, and completion.

## Technologies Used in the project

- Backend: Spring Framework (Spring Boot, Spring Security, Spring Data JPA), JWT, MySQL, Mapstruct, Jackson
- Build Tool: Maven
- Database Migration: Liquibase
- Containerization: Docker
- Testing: JUnit, Mockito, Test Containers
- API Documentation: Swagger

## Tools used for development:
- IntelliJ IDEA (Ultimate Edition)
- Postman
- GitHub
- Dropbox

## Layered Architecture
- **Controller Layer**: Handles incoming HTTP requests and communicates with clients. Controllers contain methods that process these requests and call the necessary services to execute business operations.
- **Service Layer**: Encapsulates the business logic of the application. This layer manages data processing and interaction with repositories and mappers to fulfill business requirements.
- **Repository Layer**: Manages direct interactions with the database. Repositories are responsible for querying, saving, updating, and deleting data as dictated by the application's needs.
- **Security Layer**: Oversees authentication and authorization processes. This layer ensures secure access to resources by implementing role-based access control mechanisms.
- **DTO** (Data Transfer Object) Layer: Consists of objects designed for transferring data between different layers of the application, streamlining data communication by including only necessary information.
- **Mapper Layer**: Facilitates the conversion between different object models, specifically between entities and Data Transfer Objects (DTOs), ensuring data consistency across layers.
- **Test**: Encompasses unit and integration tests that validate the functionality and correctness of various application components, including controllers, services, and repositories.

## SQL Database Diagram
Below is a representation of the database used in the project:

![task-management-db-diagram.png](task-management-db-diagram.png)

## Controllers

1. **Auth Controller**:
    - POST: /api/auth/register - User registration
    - POST: /api/auth/login - User authentication

2. **Users Controller**: Managing authentication and user registration
    - PUT: /users/{id}/role - update user role (only for admin role)
    - GET: /users/me - get my profile info
    - PUT/PATCH: /users/me - update profile info

3. **Project Controller**:
    - POST: /api/projects - Create a new project  (only for admin role)
    - GET: /api/projects - Retrieve user's projects
    - GET: /api/projects/{id} - Retrieve project details
    - PUT: /api/projects/{id} - Update project (only for admin role)
    - DELETE: /api/projects/{id} - Delete project (only for admin role)

4. **Task Controller**:
    - POST: /api/tasks - Create a new task (only for admin role)
    - GET: /api/tasks - Retrieve tasks for a project
    - GET: /api/tasks/{id} - Retrieve task details
    - PUT: /api/tasks/{id} - Update task (only for admin role)
    - DELETE: /api/tasks/{id} - Delete task (only for admin role)

5. **Comment Controller**:
    - POST: /api/comments - Add a comment to a task
    - GET: /api/comments/{taskId} - Retrieve comments for a task

6. **Attachment Controller**:
    - POST: /api/attachments - Upload an attachment to a task (File gets uploaded to Dropbox and we store the Dropbox File ID in our database)
    - GET: /api/attachments/{taskId} - Retrieve attachments for a task (Get the Dropbox File ID from the database and retrieve the actual file from Dropbox)

7. **Label Controller**:
    - POST: /api/labels - Create a new label (only for admin role)
    - GET: /api/labels - Retrieve labels
    - PUT: /api/labels/{id} - Update label (only for admin role)
    - DELETE: /api/labels/{id} - Delete label (only for admin role)

## Challenges

The biggest challenge for me personally was understanding how Dropbox works and how to use it in an application. This required studying the documentation provided by its creators, and based on that, figuring out how to write functionality specifically for my application.