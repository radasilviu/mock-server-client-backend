package com.fortech.mockapp.dummydata;

import com.fortech.mockapp.model.AddressModel;
import com.fortech.mockapp.model.ClientModel;
import com.fortech.mockapp.model.CompanyModel;
import com.fortech.mockapp.repository.AddressRepository;
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

    final int size = 1000;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        Faker faker = new Faker();

        for (int i = 0; i < size; i++) {
            String name = faker.company().name();
            String industry = faker.company().industry();
            CompanyModel company = new CompanyModel(name, industry);
            company = companyRepository.save(company);

            for (int j = 0; j < 3; j++) {
                name = faker.name().fullName();
                ClientModel client = clientRepository.save(new ClientModel(name, company));

                List<AddressModel> addresses = new ArrayList();

                for (int k = 0; k < 2; k++) {
                    String streetName = faker.address().streetName();
                    String addressNumber = faker.address().streetAddressNumber();

                    addresses.add(new AddressModel(streetName, addressNumber, client));
                }
                addressRepository.saveAll(addresses);
            }
        }
    }
}
