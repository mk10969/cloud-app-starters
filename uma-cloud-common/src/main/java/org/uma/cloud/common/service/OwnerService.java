package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.model.Owner;
import org.uma.cloud.common.repository.OwnerRepository;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository repository;


    public Owner findOne(Integer ownerCd) {
        return this.repository.findById(ownerCd).orElseThrow();
    }

    public boolean exists(Integer ownerCd) {
        return this.repository.existsById(ownerCd);
    }

    @Transactional
    public Owner save(Owner owner) {
        return this.repository.save(owner);
    }

    @Transactional
    public List<Owner> saveAll(List<Owner> owners) {
        return this.repository.saveAll(owners);
    }

    @Transactional
    public void delete(Owner owner) {
        this.repository.delete(owner);
    }
}
