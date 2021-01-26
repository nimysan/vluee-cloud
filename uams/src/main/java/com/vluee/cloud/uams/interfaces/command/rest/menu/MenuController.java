package com.vluee.cloud.uams.interfaces.command.rest.menu;


import com.vluee.cloud.uams.core.menus.service.MenuService;
import com.vluee.cloud.uams.interfaces.query.rest.menu.MenuAssembler;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final MenuAssembler menuAssembler;

    @PostMapping("/resources/menus")
    public void addTopMenu(@RequestParam String group, @RequestParam String groupName, @RequestParam String name) {
        menuService.addTopMenu(name, group, groupName);
    }

    /**
     * 添加子menu
     *
     * @param group
     * @param groupName
     * @param name
     */
    @PostMapping("/resources/menus/{menuId}/menus")
    public void addChildMenu(@PathVariable Long menuId, @RequestParam String group, @RequestParam String groupName, @RequestParam String name) {
        menuService.addChild(menuId, name, group, groupName);
    }
}
