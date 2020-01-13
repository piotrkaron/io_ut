package io.repository;

import io.entity.RentList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public enum RentListRepositoryImpl implements RentListRepository {
    INSTANCE;

    private List<RentList> rentLists = new ArrayList<>();

    @Override
    public RentList save(RentList entity) {
        if(rentLists.contains(entity)){
            rentLists.remove(entity);
        }else{
            entity.setId(UUID.randomUUID().toString());
        }
        rentLists.add(entity);
        return entity;
    }

    @Override
    public void deleteAll() {
        this.rentLists.clear();
    }
}
