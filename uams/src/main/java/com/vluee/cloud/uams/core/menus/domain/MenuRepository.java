package com.vluee.cloud.uams.core.menus.domain;

import java.util.List;
import java.util.Optional;

public interface MenuRepository {

    public List<Menu> findAll();

    void save();

    Optional<Menu> findById(Long parentId);
}

