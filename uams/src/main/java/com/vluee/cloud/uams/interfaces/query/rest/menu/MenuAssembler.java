package com.vluee.cloud.uams.interfaces.query.rest.menu;

import com.vluee.cloud.uams.core.menus.domain.Menu;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class MenuAssembler {

    public MenuVO assemble(Menu menu) {
        MenuVO build = assembleWithoutChildren(menu);
        build.setChildren(menu.getChildren().stream().map(t -> assemble(t)).collect(Collectors.toList()));
        return build;
    }

    private MenuVO assembleWithoutChildren(Menu menu) {
        return MenuVO.builder().icon(menu.getIcon()).name(menu.getName()).groupName(menu.getGroupName()).group(menu.getGroup()).id(Long.toString(menu.getId())).build();
    }
}
