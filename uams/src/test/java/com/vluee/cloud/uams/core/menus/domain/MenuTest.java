package com.vluee.cloud.uams.core.menus.domain;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class MenuTest {

    @Test
    public void testOutputMenu() {


        List<Menu> menuList = new ArrayList<>(2);

        Menu top = Menu.builder().name("表单页").group("/form").groupName("first").build();

        Menu child1 = Menu.builder().name("基础表单").group("/form").build();
        top.addChild(child1);

        menuList.add(top);
        String s = JSONUtil.toJsonPrettyStr(menuList);
        log.info("SSS");
        log.info(s);
    }

}