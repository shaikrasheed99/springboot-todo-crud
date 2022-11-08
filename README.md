# Spring Boot TODO CRUD TDD

## Gradle based spring boot application which provide APIs to create, read, update and delete the todo using test driven development.

## Features of the Application
    - Create todo
    - Read todo
    - Update todo
    - Delete todo
    - Read todos by Priority
    - Read todos by Completed Status

## APIs

### Create a Todo

* Request
```
POST /todo
Host: localhost:3000
Content-Type: application/json
{
    "id": 1,
    "description": "Sleeping",
    "completed": false,
    "priority": "high"
}
```
* Response
```
{
    "data": {
        "id": 1
    }
}
```

### Get Todo details by Todo id

* Request
```
GET /todo/{1}
Host: localhost:3000
```
* Response
```
{
    "data": {
        "id": 1,
        "description": "Sleeping",
        "completed": false,
        "priority": "high"
    }
}
```

### Update Todo details

* Request
```
PUT /todo/{1}
Host: localhost:3000
Content-Type: application/json
{
    "id": 1,
    "description": "Sleeping",
    "completed": true,
    "priority": "high"
}
```
* Response
```
{
    "data": {
        "id": 1,
        "description": "Sleeping",
        "completed": true,
        "priority": "high"
    }
}
```

### Delete a Todo by Todo id

* Request
```
DELETE /todo/{1} 
Host: localhost:3000
```
* Response
```
{
    "data": {
        "message": "Delete successfully!"
    }
}
```

### Get Todos by Priority

* Request
```
GET /todo/priority/{“high”} 
Host: localhost:3000
```
* Response
```
{
    "data": [
        {
            "id": 1,
            "description": "Sleeping",
            "completed": false,
            "priority": "high"
        }
    ]
}
```

### Get Todos by Completed Status

* Request
```
GET /todo/completed/{false} 
Host: localhost:3000
```
* Response
```
{
    "data": [
        {
            "id": 1,
            "description": "Sleeping",
            "completed": false,
            "priority": "high"
        }
    ]
}
```
