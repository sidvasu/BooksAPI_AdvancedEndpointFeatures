# BooksAPI_AdvancedEndpointFeatures

**Endpoints**

Get all books: `GET /api/books`
![Screenshot1.png](screenshots/Screenshot1.png)

Get book of specified ID: `GET /api/books/{id}`
![Screenshot2.png](screenshots/Screenshot2.png)

Create a new book: `POST /api/books/`
![Screenshot3.png](screenshots/Screenshot3.png)

Update a book: `PUT /api/books/{id}`
![Screenshot4.png](screenshots/Screenshot4.png)

Partially update a book: `PATCH /api/books/{id}`
![Screenshot5.png](screenshots/Screenshot5.png)

Remove book of specified ID: `DELETE /api/books/{id}`
![Screenshot6.png](screenshots/Screenshot6.png)

Get all books (paginated): `GET /api/books?page=<PAGE NUMBER>&pageSize=<ENTRIES PER PAGE>`
![Screenshot7.png](screenshots/Screenshot7.png)

Get all books (filtered by price, sorted, and paginated): `GET /api/books?minPrice=<MINIMUM PRICE>&maxPrice=<MAXIMUM PRICE>&sortBy=<ATTRIBUTE>&order=<ORDER>&page=<PAGE NUMBER>&pageSize=<ENTRIES PER PAGE>`
![Screenshot8.png](screenshots/Screenshot8.png)