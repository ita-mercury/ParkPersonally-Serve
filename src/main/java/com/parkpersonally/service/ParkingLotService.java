package com.parkpersonally.service;

import com.parkpersonally.exception.NoSuchParkingLotException;
import com.parkpersonally.exception.UpdateParkingLotCapacitySmallerException;
import com.parkpersonally.model.ParkingLot;
import com.parkpersonally.repository.ParkingLotRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Data
@Service
public class ParkingLotService {

    @Autowired
    private ParkingLotRepository repository;

    public ParkingLot saveService(ParkingLot parkingLot){
        return repository.save(parkingLot);
    }

    public ParkingLot updateParkingLot(long id, ParkingLot updateParkingLot){
        ParkingLot parkingLot = repository.findById(id)
                .orElseThrow(() -> new NoSuchParkingLotException("没有找到对应的停车场"));

        // todo 没有解决并发问题
        if (updateParkingLot.getCapacity() >= parkingLot.getCapacity()) {
            parkingLot.setRestCapacity(updateParkingLot.getCapacity()-(parkingLot.getCapacity()-parkingLot.getRestCapacity()));
            parkingLot.setCapacity(updateParkingLot.getCapacity());
        }else throw new UpdateParkingLotCapacitySmallerException("无法把停车场容量缩小");

        return repository.save(parkingLot);
    }

    public List<ParkingLot> findParkingLots() {
        return repository.findAll();
    }

//    public ParkingLot changeParkingLotStatus(long id, ParkingLot updateParkingLot){
//        ParkingLot parkingLot = repository.findById(id)
//                .orElseThrow(() -> new NoSuchParkingLotException("没有找到对应的停车场"));
//
//        switch (updateParkingLot.getStatus()){
//            case ParkingLot.LOT_STATUS_FREEZE: {
//                if (parkingLot.getStatus() == )
//            }
//        }
//    }
//
//    private int switchParkingLotStatus

    public ParkingLotService() {
    }

    public ParkingLotService(ParkingLotRepository parkingLotRepository) {
        this.repository = parkingLotRepository;
    }
}
