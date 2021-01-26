package com.vluee.cloud.uams.core.menus.domain;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Menu {

    private Long id;

    private String name;

    private String group;

    private String groupName;

    private String icon;

    @Builder.Default
    private List<Menu> children = new ArrayList<>();

    public void addChild(Menu menu) {
        this.children.add(menu);
    }


}
