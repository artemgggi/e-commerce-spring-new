package com.artemgggi.webshop.service;

import com.artemgggi.webshop.dto.CustomerDto;
import com.artemgggi.webshop.dto.CustomerRepository;
import com.artemgggi.webshop.dto.RoleRepository;
import com.artemgggi.webshop.model.Customer;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomerService {

    private final RoleRepository repository;

    private final CustomerRepository customerRepository;

    public CustomerService(RoleRepository repository, CustomerRepository customerRepository) {
        this.repository = repository;
        this.customerRepository = customerRepository;
    }

    public CustomerDto save(CustomerDto customerDto) {

        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUsername(customerDto.getUsername());
        customer.setPassword(customerDto.getPassword());
        customer.setRoles(Arrays.asList(repository.findByName("CUSTOMER")));

        Customer customerSave = customerRepository.save(customer);
        return mapperDTO(customerSave);
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username);
    }

    public Customer saveInfor(Customer customer) {
        Customer customer1 = customerRepository.findByUsername(customer.getUsername());
        customer1.setAddress(customer.getAddress());
        customer1.setCity(customer.getCity());
        customer1.setCountry(customer.getCountry());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        return customerRepository.save(customer1);
    }

    private CustomerDto mapperDTO(Customer customer){
        CustomerDto customerDto = new CustomerDto();
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPassword(customer.getPassword());
        customerDto.setUsername(customer.getUsername());
        return customerDto;
    }
}
