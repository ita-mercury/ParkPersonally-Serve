package com.parkpersonally.service;

import com.parkpersonally.dto.ParkingOrderDto;
import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.Customer;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.CustomerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import com.parkpersonally.repository.ParkingOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;
    @Autowired
    private ParkingOrderRepository parkingOrderRepository;

    public CustomerService(ParkingBoyRepository parkingBoyRepository, ParkingOrderRepository parkingOrderRepository) {
        this.parkingBoyRepository=parkingBoyRepository;
        this.parkingOrderRepository=parkingOrderRepository;
    }

    public List<Tag> getAllTags(long parkingBoyId) {
      ParkingBoy parkingBoy = parkingBoyRepository.findById(parkingBoyId).orElseThrow(()->new NoSuchParkingBoyException("抱歉,没有查到停车员"));
        return parkingBoy.getTags();
    }

    public Customer findOneById(long customerId) {
       Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new NoSuchParkingBoyException("没有找到customer信息")
        );
        return customer;
    }

    public List<ParkingOrderDto> getAllOrders(long customerId) {
            List<ParkingOrder> parkingFetchOrders = parkingOrderRepository.findAllByCustomerId(customerId);
            List<ParkingOrderDto> result = new ArrayList<>();
            for(ParkingOrder item : parkingFetchOrders){
                result.add(new ParkingOrderDto(item));
            }
            return result;
    }


}
