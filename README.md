# REST API
## Авторы книг  
POST /authors - добавление автора  
>*Request body example:*  
```json
{
    "name": "William Shakespeare"
}
```
GET /authors - получение авторов  
GET /authors/{id} - получение автора по его id  
PUT /authors/{id} - изменение автора 
>*Request body example:*
```json
{
    "name": "Leo Tolstoy"
}
```
DELETE /authors/{id} - удаление автора
## Издательские дома
POST /publishing_houses - добавление издательтва
>*Request body example:*
```json
{
    "name": "Academic press"
}
```
GET /publishing_houses - получение издательств  
GET /publishing_houses/{id} - получение издательства по его id  
PUT /publishing_houses/{id} - изменение издательства  
>*Request body example:*
```json
{
    "name": "Springer"
}
```
DELETE /publishing_houses/{id} - удаление издательства
## Книги
POST /books - добавление книги
>*Request body example:*
```json
{
  "name": "Romeo and Juliet",
  "publishingHouse": 1,
  "authors":[1,2]
}
```
GET /books - получение книг  
GET /books/{id} - получение книги по ее id  
PUT /books/{id} - изменение книги
>*Request body example:*
```json
{
  "name": "Updated book name",
  "publishingHouse": 7,
  "authors":[4]
}
```
DELETE /books/{id} - удаление книги