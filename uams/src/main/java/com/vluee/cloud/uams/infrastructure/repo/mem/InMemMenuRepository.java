package com.vluee.cloud.uams.infrastructure.repo.mem;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import com.google.common.base.Joiner;
import com.vluee.cloud.uams.core.menus.domain.Menu;
import com.vluee.cloud.uams.core.menus.domain.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class InMemMenuRepository implements MenuRepository {


    private List<Menu> menuStore = new ArrayList<>();

    @Override
    public List<Menu> findAll() {
        return menuStore;
    }

    @PostConstruct
    private void load() {
        try {
            List<String> strings = IOUtils.readLines(new FileInputStream("c:\\temp\\menu.json"), "UTF-8");
            menuStore = JSONUtil.toList(JSONUtil.parseArray(Joiner.on("\r\n").join(strings)), Menu.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save() {
        String s = JSONUtil.toJsonPrettyStr(menuStore);
        try {
            IoUtil.writeUtf8(new FileOutputStream("c:\\temp\\menu.json"), true, s);
        } catch (FileNotFoundException e) {
            log.info("save failed", e);
        }
    }

    @Override
    public Optional<Menu> findById(Long parentId) {
        return search(parentId);
    }

    private Optional<Menu> search(Long id) {
        return menuStore.stream().map(t -> contains(t, id)).filter(Objects::nonNull).findFirst();
    }

    private Menu contains(Menu menu, Long id) {
        if (id.equals(menu.getId())) {
            return menu;
        }
        for (Menu child : menu.getChildren()) {
            Menu matched = contains(child, id);
            if (matched != null) {
                return matched;
            }
        }
        return null;
    }
}
