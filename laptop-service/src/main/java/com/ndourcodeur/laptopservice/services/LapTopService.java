package com.ndourcodeur.laptopservice.services;


import com.ndourcodeur.laptopservice.dto.LaptopRequest;
import com.ndourcodeur.laptopservice.entity.Laptop;
import com.ndourcodeur.laptopservice.model.ResponseTemplateLapTopWithUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LapTopService {

    public Laptop addLapTop(LaptopRequest request);
    public Laptop editLapTop(Long id, LaptopRequest request);
    public List<Laptop> findAllLapTops();
    public Laptop findLapTop(Long id);
    public List<Laptop> findByUserId(Long userId);
    public void deleteLapTop(Long id);

    public ResponseTemplateLapTopWithUser findLapTopWithUser(Long lapTopId);


}
