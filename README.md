# Goods Offer REST API
(by Sandun Lewke Bandara)

This REST Service developed using Spring Boot,Spring REST and Spring Data with HSQLDB/H2 embedded database support.

There are 5 REST endpoints for,

  1.) Create new account - POST.

  2.) Make an offer of products - POST.

  3.) List all Offered Products - GET. 

  4.) Search products matching description like - GET.

  5.) Search offered product by ID - GET.

To build the project using maven,
	run : mvn clean install

To run the application on command line,
	run: java -jar offer-goods-rest-api 0.0.1-SNAPSHOT.jar & 


SAMPLE END POINTS

## 1. Create a Merchant Account

POST http://localhost:8080/project-offers-api/merchants

HTTP REQUEST HEADERS: Content-Type: application/json

REQUEST BODY

```json
{
   "name":"Sandun Lewke Bandara",
   "address":"10 City Road, London SW1 2SD",
   "phoneNumber":"02081765245"
}
```
RESPONSE STATUS - 201

RESPONSE BODY -

response body will return a MerchantAccount json object with a new account ID.

```json
{
  "id": 1,
   "name":"Sandun Lewke Bandara",
   "address":"10 City Road, London SW1 2SD",
   "phoneNumber":"02081765245"
}
```
## 2. Offer a Product

POST http://localhost:8080/project-offers-api/merchants/{merchantId}/products

HTTP REQUEST HEADERS: Content-Type: application/json

`REQUEST BODY
```json
{
   "name":"PS 4 Slim 500 GB",
   "description":"PS 4 Slim 500 GB available to buy which is in grade A condition, used only 1 month",
   "category":"GAME_CONSOLE",
   "price":{
      "amount":99.99,
      "currency":"GBP"
   }
}
```
RESPONSE STATUS - 201

RESPONSE BODY -

response body will contain offered Product json object with newly created ID.
```json
{
  "id": 4,
   "name":"PS 4 Slim 500 GB",
   "description":"PS 4 Slim 500 GB available to buy which is in grade A condition, used only 1 month",
   "category":"GAME_CONSOLE",
   "price":{
      "amount":99.99,
      "currency":"GBP"
   }
}
```

## 3. List all Offered Products 

GET http://localhost:8080/project-offers-api/products

RESPONSE STATUS - 200

RESPONSE BODY -

```json
[
    {
        "id": 1,
        "name": "Harry Potter Book",
        "description": "Harry Potter and the Chamber of Secrets is the second novel in the Harry Potter series, written by J. K. Rowling.",
        "category": "BOOK",
        "price": {
            "amount": 52489.07,
            "currency": "GBP"
        }
    },
    {
        "id": 2,
        "name": "PS 3",
        "description": "Play Staton 3 by Sony",
        "category": "GAME_CONSOLE",
        "price": {
            "amount": 89551.95,
            "currency": "EUR"
        }
    }
]
```

## 4. List all Offered Products matched by description

GET http://localhost:8080/project-offers-api/products/find-by-description?descriptionLike=Harry

RESPONSE STATUS - 200

RESPONSE BODY -

```json
[
  {
    "id": 1,
    "name": "Harry Potter Book",
    "description": "Harry Potter and the Chamber of Secrets is the second novel in the Harry Potter series, written by J. K. Rowling.",
    "category": "BOOK",
    "price": {
      "amount": 71655.59,
      "currency": "GBP"
    }
  }
]
```

## 5. Find offered product by ID.

GET http://localhost:8080/project-offers-api//products/{productId}

RESPONSE STATUS - 200

RESPONSE BODY -

```json
[
  {
    "id": 6,
    "name": "Harry Potter Book",
    "description": "Harry Potter and the Chamber of Secrets is the second novel in the Harry Potter series, written by J. K. Rowling.",
    "category": "BOOK",
    "price": {
      "amount": 71655.59,
      "currency": "GBP"
    }
  }
]
```


RESPONSE BODY WHEN OFFERED PRODUCT BY ID NOT FOUND -

RESPONSE STATUS - 404
```json
[
{
  "status": "NOT_FOUND",
  "message": "Offered product by ID [305] is not found!"
}
]
```