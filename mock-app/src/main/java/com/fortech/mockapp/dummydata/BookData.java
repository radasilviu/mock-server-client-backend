package com.fortech.mockapp.dummydata;

import com.fortech.mockapp.entities.Book;
import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.BookRepository;
import com.fortech.mockapp.repository.CompanyRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookData implements ApplicationListener<ApplicationContextEvent> {

    final int size = 1000;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        List<Book> books = new ArrayList();
        Faker faker = new Faker();

        for (int i = 0; i < size; i++) {
            String title = faker.book().title();
            String author = faker.book().author();
            String category = faker.book().genre();
            Integer price = faker.number().numberBetween(50, 500);
            books.add(new Book(title, author, category,price));
        }
        bookRepository.saveAll(books);
    }
}
