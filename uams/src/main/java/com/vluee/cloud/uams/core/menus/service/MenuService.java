package com.vluee.cloud.uams.core.menus.service;

import com.vluee.cloud.commons.common.data.id.LongIdGenerator;
import com.vluee.cloud.uams.core.menus.domain.Menu;
import com.vluee.cloud.uams.core.menus.domain.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;

    private final LongIdGenerator longIdGenerator;

    public List<Menu> listTopMenus() {
        return menuRepository.findAll();
    }

    public void addTopMenu(String name, String group, String groupName) {
        Menu menu = buildMenu(name, group, groupName);
        menuRepository.findAll().add(menu);
        menuRepository.save();
    }

    private Menu buildMenu(String name, String group, String groupName) {
        return Menu.builder().id(longIdGenerator.nextId()).name(name).groupName(groupName).group(group).build();
    }

    public void addChild(Long parentId, String name, String group, String groupName) {
        Menu menu = menuRepository.findById(parentId).orElseThrow(RuntimeException::new);
        menu.addChild(buildMenu(name, menu.getGroup() + "/" + group, groupName));
        menuRepository.save();
    }
}
