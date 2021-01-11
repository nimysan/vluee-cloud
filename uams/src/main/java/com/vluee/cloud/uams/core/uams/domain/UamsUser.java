package com.vluee.cloud.uams.core.uams.domain;

import com.vluee.cloud.commons.common.data.TenancyAndAuditAware;
import com.vluee.cloud.uams.core.uams.service.UamsServiceHolder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Entity
public class UamsUser extends TenancyAndAuditAware implements BrandOwner {

    @Deprecated
    public UamsUser() {
    }

    public UamsUser(String tenancyId, String userCode) {
        super(tenancyId);
        this.userCode = userCode;
        this.directRoles = new HashSet<>(1);
        this.userGroups = new HashSet<>(1);
    }

    @Id
    @GeneratedValue(generator = "customGenerationId")
    @GenericGenerator(name = "customGenerationId", strategy = "cn.jzdata.aistore.user.core.uams.service.UamsIdGenerator")
    private String id;

    /**
     * 账号说明
     */
    private String description;

    /**
     * 账号昵称
     */
    private String nickName;

    /**
     * 用户在公司的工号, 可以为空
     */
    private String employeeId;

    /**
     * 全局唯一的一个UserCode
     */
    private String userCode;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户所属的用户组， 一个用户可以属于多个用户组
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "uams_user_group_maps", joinColumns = {@JoinColumn(name = "user_id")})
    private Set<UamsUserGroup> userGroups;

    /**
     * 用户与角色的直接绑定
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "uams_user_role_maps", joinColumns = {@JoinColumn(name = "user_id")})
    private Set<UamsRole> directRoles;

    /**
     * 指定用户是否拥有所有品牌， 默认不拥有
     */
    private boolean ownAllBrands = false;

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public Collection<UamsRole> getDirectRoles() {
        return Collections.unmodifiableSet(this.directRoles);
    }

    public Set<UamsUserGroup> getUserGroups() {
        return Collections.unmodifiableSet(userGroups);
    }

    public void addRole(UamsRole uamsRole) {
        this.directRoles.add(uamsRole);
    }

    public void addGroup(UamsUserGroup uamsUserGroup) {
        this.userGroups.add(uamsUserGroup);
    }

    public boolean isOwnAllBrands() {
        return ownAllBrands;
    }

    public void setOwnAllBrands() {
        this.ownAllBrands = true;
    }

    public void cancelOwnAllBrands() {
        this.ownAllBrands = false;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<String> getRoleKeys() {
        List<String> collect = Stream.concat(getDirectRoles().stream(), getUserGroups().stream().flatMap(uamsUserGroup -> uamsUserGroup.getRoles().stream())).map(t -> t.getId()).collect(Collectors.toList());
        return Collections.unmodifiableList(collect);
    }

    @Override
    public Collection<String> getBrands() {
        return StreamSupport.stream(UamsServiceHolder.uamsService().getBrandByUser(this.id).spliterator(), false).collect(Collectors.toList());
    }
}
