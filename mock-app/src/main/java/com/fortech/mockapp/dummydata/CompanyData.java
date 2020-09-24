package com.fortech.mockapp.dummydata;

import com.fortech.mockapp.model.ClientModel;
import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.ClientRepository;
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

    final int size = 10000;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        Faker faker = new Faker();

        for (int i = 0; i < size; i++) {
            List<ClientModel> clients = new ArrayList();

            String name = faker.company().name();
            String industry = faker.company().industry();
            CompanyModel company = new CompanyModel(name, industry);
            company = companyRepository.save(company);

            for (int j = 0; j < 3; j++) {
                name = faker.name().fullName();
                clients.add(new ClientModel(name, company));
            }

            clientRepository.saveAll(clients);
        }
    }
}
