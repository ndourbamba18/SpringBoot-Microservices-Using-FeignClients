package com.ndourcodeur.laptopservice.services;

import com.ndourcodeur.laptopservice.dto.LaptopRequest;
import com.ndourcodeur.laptopservice.entity.Laptop;
import com.ndourcodeur.laptopservice.exception.ResourceNotFoundException;
import com.ndourcodeur.laptopservice.model.ResponseTemplateLapTopWithUser;
import com.ndourcodeur.laptopservice.model.User;
import com.ndourcodeur.laptopservice.repository.LapTopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;

@Component
@Transactional
@Slf4j
public class LapTopServiceImpl implements LapTopService {

    @Autowired
    RestTemplate restTemplate;
    private final LapTopRepository lapTopRepository;

    public LapTopServiceImpl(LapTopRepository lapTopRepository) {
        this.lapTopRepository = lapTopRepository;
    }

    @Override
    public Laptop addLapTop(LaptopRequest request) {
        log.info("Inside addLopTop of LapTopService");
        Laptop laptop = new Laptop();
        laptop.setId(request.getId());
        laptop.setLapTopName(request.getLapTopName());
        laptop.setLapTopPrice(request.getLapTopPrice());
        laptop.setLapTopBrand(request.getLapTopBrand());
        laptop.setIsInStock(request.getIsInStock());
        laptop.setDescription(request.getDescription());
        laptop.setUserId(request.getUserId());
        return lapTopRepository.save(laptop);
    }

    @Override
    public Laptop editLapTop(Long id, LaptopRequest request) {
        log.info("Inside editLopTop of LapTopService");
        Laptop laptop = lapTopRepository.findById(id)
                        .orElseThrow( () -> new ResourceNotFoundException("LapTop does not exist with ID:"+id));
        laptop.setId(request.getId());
        laptop.setLapTopName(request.getLapTopName());
        laptop.setLapTopPrice(request.getLapTopPrice());
        laptop.setLapTopBrand(request.getLapTopBrand());
        laptop.setIsInStock(request.getIsInStock());
        laptop.setDescription(request.getDescription());
        laptop.setUserId(request.getUserId());
        return lapTopRepository.save(laptop);
    }

    @Override
    public List<Laptop> findAllLapTops() {
        log.info("Inside findAllLopTop of LapTopService");
        return lapTopRepository.findAll();
    }

    @Override
    public Laptop findLapTop(Long id) {
        log.info("Inside findLopTop of LapTopService");
        return lapTopRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("LapTop does not exist with ID:"+id));
    }

    @Override
    public List<Laptop> findByUserId(Long userId) {
        log.info("Inside findByUserId of LapTopService");
        return lapTopRepository.findByUserId(userId);
    }

    @Override
    public void deleteLapTop(Long id) {
        log.info("Inside deleteLopTop of LapTopService");
        Laptop laptop = lapTopRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("LapTop does not exist with ID:"+id));
        lapTopRepository.delete(laptop);
    }

    @Override
    public ResponseTemplateLapTopWithUser findLapTopWithUser(Long lapTopId) {
        log.info("Inside findLapTopWithUser of LapTopService");
        ResponseTemplateLapTopWithUser response = new ResponseTemplateLapTopWithUser();
        Laptop laptop = findLapTop(lapTopId);
        User user = restTemplate.getForObject("http://localhost:8100/api/v1/users/user-detail/" + laptop.getUserId(), User.class);
        response.setLaptop(laptop);
        response.setUser(user);
        return response;
    }
}
