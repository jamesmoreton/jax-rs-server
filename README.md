# JAX-RS Server

Simple Java REST API interface using [JAX-RS](https://en.wikipedia.org/wiki/Jakarta_RESTful_Web_Services).

- JAX-RS (Jersey) REST server
- Compiles & runs under Java 17
- Built with Maven
- Google Guice as dependency injection framework

## Setup

To run the server, from the root directory `jax-rs-server`:

```
mvn clean install
mvn exec:java -Dexec.mainClass=com.jamesmoreton.ServerMainline
```

...or run from your favourite IDE.

## cURL examples

#### Create user

```
curl -X POST 'http://localhost:1234/api/v1/users' -H 'Content-Type: application/json' --data-raw '{
    "userType": "BASIC",
    "dateOfBirth": "2000-01-01",
    "countryCode": "GB"
}'
```

#### Get user

```
curl -X GET 'http://localhost:1234/api/v1/users/8d43329a-ea11-41be-887a-2a5696514bf8'
```

#### Get users

```
curl -X GET 'http://localhost:1234/api/v1/users?userType=PREMIUM&countryCode=US'
```

#### Update user

```
curl -X PUT 'http://localhost:1234/api/v1/users/8d43329a-ea11-41be-887a-2a5696514bf8' -H 'Content-Type: application/json' --data-raw '{
    "userType": "PREMIUM"
}'
```

#### Delete user

```
curl -X DELETE 'http://localhost:1234/api/v1/users/8d43329a-ea11-41be-887a-2a5696514bf8'
```