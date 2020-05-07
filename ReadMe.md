# Web Store

## Definition

Store application. API designed in RESTful manner.
 
Register new user. Example request: {“email”:”my@email.com”, “password”:”123”}<br>
POST /api/register
 
Login into system. Example request: {“email”:”my@email.com”, “password”:”123”} <br>
POST /api/login
 
Get all products in store.<br>
GET /api/products
 
Add item to cart. Example request: {“id”:”363”, “quantity”:”2”} <br>
POST /api/cart

Get all products in cart:<br>
GET /api/cart
 
Remove an item from user’s cart:<br>
DELETE /api/cart/{id}
 
Modify cart item. Example request: {“id”:2, quantity: 3}<br>
PATCH /api/cart/{id}

## Istall

Choose appropriate driver in the pom.xml<br>
Pick settings to a chosen database in resources.application.properties for<br>
spring.datasource.url<br>
spring.datasource.driver-class


Import dependencies

Create database web_store with user web_store_user and import into db<br>
sql/dump.sql

Start the application
