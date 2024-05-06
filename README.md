# Online Bookstore Microservices

This project implements an online bookstore system using a microservices architecture. The system is composed of three main services: `Book Catalog Service`, `Order Service`, and `Customer Service`.

---

## Services

### 1. Book Catalog Service

Manages the book catalog of the bookstore. Each book can have attributes like `id`, `title`, `author`, `price`, and `quantity`.

**Endpoints:**

- `POST /api/books`: Add a new book.
- `PUT /api/books/{bookId}`: Update an existing book.
- `DELETE /api/books/{bookId}`: Delete a book.
- `GET /api/books/{bookId}`: Retrieve details of a book.
- `GET /api/books?page=0&size=5`: Retrieve a page of books.

### 2. Order Service

Handles order-related operations. An order should have an `id`, `custmoerId`, `bookId`, and `quantity`.

**Endpoints:**

- `POST /api/orders`: Place a new order.
- `DELETE /api/orders/{orderId}`: Cancel an order.
- `GET /api/orders/{orderId}`: Retrieve details of an order.
- `GET /api/orders/customer/{customerId}`: Retrieve all orders of a customer.

### 3. Customer Service

Manages customer data and operations. A customer can have attributes like `id`, `name`, `email`, and `address`.

**Endpoints:**

- `POST /api/customers`: Create a new customer.
- `PUT /api/customers/{customerId}`: Update customer details.
- `GET /api/customers/{customerId}`: Retrieve customer details.

---

## Use Cases

### Customer Browses Books

1. The client makes a `GET` request to `/api/books` of the Book Catalog Service.
2. The Book Catalog Service returns a list of all books.

### Customer Places an Order

1. The client makes a `POST` request to `/api/orders` of the Order Service with `customerId` and `bookId`.
2. The Order Service verifies the customer's existence by making a `GET` request to `/api/customers/{customerId}` of the Customer Service.
3. The Customer Service returns customer details.
4. The Order Service verifies the book's existence and availability by making a `GET` request to `/api/books/{bookId}` of the Book Catalog Service.
5. The Book Catalog Service returns book details.
6. If customer and book exist and the book is available, the Order Service creates a new order and updates the book's quantity by making a `PUT` request to `/api/books/{bookId}` of the Book Catalog Service.
7. The Book Catalog Service updates the book's quantity and returns the updated book details.
8. The Order Service returns the order details to the client.

### Customer Updates Their Details

1. The client makes a `PUT` request to `/api/customers/{customerId}` of the Customer Service with updated customer details.
2. The Customer Service updates the customer's details and returns the updated customer details.

---
