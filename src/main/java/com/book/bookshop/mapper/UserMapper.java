package com.book.bookshop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.book.bookshop.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户接口
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
}
