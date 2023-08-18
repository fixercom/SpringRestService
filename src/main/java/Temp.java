import entity.Author;
import service.AuthorService;
import service.impl.AuthorServiceImpl;

public class Temp {
    public static void main(String[] args) {
        AuthorService authorService = AuthorServiceImpl.getInstance();

        Author author1 = new Author("Alex Mil");
        Author author2 = new Author("Jen Pol");
        Author author3 = new Author("Robert Martin");
        Author author4 = new Author("Updated");
        authorService.addAuthor(author1);
        authorService.addAuthor(author2);
        authorService.addAuthor(author3);
        System.out.println(authorService.getAllAuthors());
        authorService.updateAuthor(author2.getId(),author4);
        System.out.println(authorService.getAllAuthors());

        System.out.println(author1.getId());
        authorService.deleteAuthor(author1.getId());
        System.out.println(authorService.getAllAuthors());

        System.out.println(authorService.getAuthorById(author2.getId()));


    }
}
