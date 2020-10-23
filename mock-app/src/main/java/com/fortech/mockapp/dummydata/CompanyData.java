package com.fortech.mockapp.dummydata;

import com.fortech.mockapp.entities.CompanyModel;
import com.fortech.mockapp.repository.CompanyRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompanyData implements ApplicationListener<ApplicationContextEvent> {

    final int size = 1000;

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        List<CompanyModel> companies = new ArrayList();
        Faker faker = new Faker();

        for (int i = 0; i < size; i++) {
            String name = faker.company().name();
            String industry = faker.company().industry();
            companies.add(new CompanyModel(name, industry));
        }
        companyRepository.saveAll(companies);
    }
}
