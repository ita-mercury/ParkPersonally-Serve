package com.parkpersonally.controller;

import com.parkpersonally.model.Customer;
import com.parkpersonally.model.Tag;
import com.parkpersonally.service.CustomerService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @MockBean
    private CustomerService service;

    @Autowired
    private MockMvc mvc;

    public void should_return_new_parking_order_when_add_new_parking(){
        Customer customer = new Customer("vinger@163.com", "123456",
                "111111", "555");
        List<Tag> tags = new ArrayList<>();



    }
}
