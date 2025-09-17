# MemoCards

The project consists of a simple application for creating flashcards to study languages.

## Tech Stack

**Back-end:** Java, Spring Boot

**Database:** MongoDB

## Functionalities

- The system have two types of users: ADMIN and BASIC
  - ADMIN users can view all users and flashcards and delete any of them. An admin account is configured when the application is run, if it doesn't already exists.
  - BASIC users only have access to their own user information and flashcards.
- The system allow any user to:
  - create and login to an account
  - create, view, edit and delete cards

## Database

- User: username, password, role, createdAt, deletedAt.
- Flashcards: id, expression , meaning, example, author, updatedAt.

## Run Locally

To run this project, you need:

- Java: JDK 17
- Git: to clone the repository
- Docker (Optional): to run the application in a container

Clone the project

```bash
  git clone https://github.com/ericarfs/memocards-spring
```

Update application.properties file with the mongodb variable

```bash
  spring.data.mongodb.uri: <mongodb uri>
```

Compile the project

```bash
  mvn clean install
```

Run the project

```bash
  mvn spring-boot:run
```

(Alternative) Running with Docker Compose

```bash
  docker compose up
```

(Alternative) Running a Docker image

```bash
  docker pull ericarfs/memocards-spring
```
```bash
  docker run ericarfs/memocards-spring
```

## API Reference

### Authentication

#### Register

```http
  POST /auth/register
```

| Body       | Type     | Description   |
| :--------- | :------- | :------------ |
| `username` | `string` | **Required**. |
| `password` | `string` | **Required**. |

#### Login

```http
  POST /api/auth/token
```

| Body       | Type     | Description   |
| :--------- | :------- | :------------ |
| `username` | `string` | **Required**. |
| `password` | `string` | **Required**. |

#### Refresh token

```http
  POST /api/auth/token/refresh
```

| Body       | Type     | Description   |
| :--------- | :------- | :------------ |
| `refreshToken` | `string` | **Required**. |

### Authenticated as ADMIN User

#### Get all users

```http
  GET /admin/users
```

#### Get an user

```http
  GET /admin/users/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `string` | **Required**. Id of user to fetch |

#### Delete an user

```http
  DELETE /admin/users/${id}
```

| Parameter | Type     | Description                        |
| :-------- | :------- | :--------------------------------- |
| `id`      | `string` | **Required**. Id of user to delete |

#### Get all flashcards

```http
  GET /admin/flashcards
```

#### Get a flashcard

```http
  GET /admin/flashcards/${id}
```

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `string` | **Required**. Id of flashcard to fetch |

#### Delete a flashcard

```http
  DELETE /admin/flashcard/${id}
```

| Parameter | Type     | Description                             |
| :-------- | :------- | :-------------------------------------- |
| `id`      | `string` | **Required**. Id of flashcard to delete |

### Authenticated as ADMIN or BASIC User

#### Get authenticated user

```http
  GET /api/users
```

#### Delete authenticated user

```http
  DELETE /api/users
```

#### Get all flashcards from authenticated user

```http
  GET /api/flashcards
```

#### Get a flashcard from authenticated user

```http
  GET /api/flashcards/${id}
```

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `string` | **Required**. Id of flashcard to fetch |

#### Insert a new flashcard for authenticated user

```http
  POST /api/flashcards
```

| Body         | Type     | Description                                                  |
| :----------- | :------- | :----------------------------------------------------------- |
| `expression` | `string` | **Required**. Expression in the studied language to be added |
| `meaning`    | `string` | **Required**. Meaning of the expression to be added          |
| `example`    | `string` | Example of the expression used in a sentense                 |

#### Update a flashcard from authenticated user

```http
  PUT /api/flashcards/${id}
```

| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `string` | **Required**. Id of flashcard to fetch |

| Body         | Type     | Description                                                  |
| :----------- | :------- | :----------------------------------------------------------- |
| `expression` | `string` | **Required**. Expression in the studied language to be added |
| `meaning`    | `string` | **Required**. Meaning of the expression to be added          |
| `example`    | `string` | Example of the expression used in a sentense                 |

#### Delete a flashcard from authenticated user

```http
  DELETE /api/flashcard/${id}
```

| Parameter | Type     | Description                             |
| :-------- | :------- | :-------------------------------------- |
| `id`      | `string` | **Required**. Id of flashcard to delete |

## Running Tests

To run tests, run the following command

```bash
  mvn test
```
