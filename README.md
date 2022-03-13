# banking-api-sample
A simple spring app serving a banking API

API Endpoints:
- Create a new customer.
- Create a new bank account for a customer, with an initial deposit amount. A single customer may have multiple bank accounts.
- Transfer amounts between any two accounts, including those owned by different customers.
- Retrieve balances for a given account.
- Retrieve transfer history for a given account.

<p><i>for postman collection, refer to banking-api.postman.json</i></p>

To run:
> mvn clean install spring-boot:run