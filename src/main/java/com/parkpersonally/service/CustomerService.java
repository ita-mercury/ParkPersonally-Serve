package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingBoyException;
import com.parkpersonally.model.ParkingBoy;
import com.parkpersonally.model.ParkingOrder;
import com.parkpersonally.model.Tag;
import com.parkpersonally.repository.CustomerRepository;
import com.parkpersonally.repository.ParkingBoyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customerService")
public class CustomerService {

    @Autowired
    @Qualifier("customerRepository")
    private CustomerRepository customerRepository;
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    public CustomerService(ParkingBoyRepository parkingBoyRepository) {
        this.parkingBoyRepository=parkingBoyRepository;
    }

    public List<Tag> getAllTags(long parkingBoyId) {
      ParkingBoy parkingBoy = parkingBoyRepository.findById(parkingBoyId).orElseThrow(()->new NoSuchParkingBoyException("抱歉,没有查到停车员"));
        return parkingBoy.getTags();
    }
}
