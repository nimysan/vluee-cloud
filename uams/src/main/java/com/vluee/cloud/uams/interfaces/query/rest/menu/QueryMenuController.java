package com.vluee.cloud.uams.interfaces.query.rest.menu;

import com.vluee.cloud.uams.core.menus.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class QueryMenuController {

    private final MenuService menuService;
    private final MenuAssembler menuAssembler;

    /**
     * 获取 menu类型的资源
     *
     * @return
     */
    @GetMapping("/resources/menus")
    public List<MenuVO> getMenus() {
        return menuService.listTopMenus().stream().map(t -> menuAssembler.assemble(t)).collect(Collectors.toList());
    }

}
