package cn.xiao.zufang.repository;

import cn.xiao.zufang.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*
角色数据dao
 */
public interface RoleRepository extends CrudRepository<Role,Long> {
    List<Role> findRolesByUserId(Long userId);
}
