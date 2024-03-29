package org.uma.cloud.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.uma.cloud.common.entity.DiffOwner;
import org.uma.cloud.common.repository.DiffOwnerRepository;

import java.util.List;

@Service
public class DiffOwnerService {

    @Autowired
    private DiffOwnerRepository repository;


    public DiffOwner findOne(Integer ownerCd) {
        return this.repository.findById(ownerCd).orElseThrow();
    }

    public boolean exists(Integer ownerCd) {
        return this.repository.existsById(ownerCd);
    }

    @Transactional
    public DiffOwner save(DiffOwner diffOwner) {
        return this.repository.save(diffOwner);
    }

    @Transactional
    public List<DiffOwner> saveAll(List<DiffOwner> diffOwners) {
        return this.repository.saveAll(diffOwners);
    }

    @Transactional
    public void delete(DiffOwner diffOwner) {
        this.repository.delete(diffOwner);
    }
}
