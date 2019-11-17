# Shopping Cart API

Shopping cart solution where you can create Users, Items and Cart.
This project is the back-end of the application, you can access the front-end here: https://github.com/priscilaluz/shopping-cart-web

## Prerequisites

- Java 8
- Maven
- MongoBD

## Build/Run the API

#### In the IDE
- Run the "Clear and Build"
- Run the file ShoppingCartApiApplication.java

#### In the navigate
- Access the endpoints in the path: http://localhost:8080/api/

## Solution
Four Json documents were created in mongoDB:
- The user has an ID, a name, an email and a ShoppingCart.
- ShoppingCart has an ID, a total, and a Shopping list.
- Shopping has an ID, a value and an Item.
- The item has an ID, a name and a value.

In the application there are four pages
- User: CRUD
- Item: CRUD
- Login: Allows login with email and username
- Shopping carts: Add item to logged in user's cart

## Authors

* Priscila Luz
