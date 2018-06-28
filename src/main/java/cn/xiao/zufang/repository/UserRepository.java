package cn.xiao.zufang.repository;

import cn.xiao.zufang.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.security.PrivateKey;
import java.util.PrimitiveIterator;


public interface UserRepository extends CrudRepository<User,Long> {
    User findByName(String userName);
}
