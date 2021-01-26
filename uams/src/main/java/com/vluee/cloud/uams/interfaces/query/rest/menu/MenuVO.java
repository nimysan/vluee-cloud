package com.vluee.cloud.uams.interfaces.query.rest.menu;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class MenuVO {

    private String name;

    private String group;

    private String groupName;

    private String id;

    private String icon;

    @Builder.Default
    private List<MenuVO> children = new ArrayList<>();

    public void addChild(MenuVO menu) {
        this.children.add(menu);
    }
}
