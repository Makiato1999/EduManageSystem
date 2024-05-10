package com.easybytes.easyschool.repository;

import com.easybytes.easyschool.model.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HolidaysRepository extends CrudRepository<Holiday, String> {

/* before example 35
public class HolidaysRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public HolidaysRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Holiday> findAllHolidays() {
        String sql = "SELECT * FROM holidays";
        var rowMapper = BeanPropertyRowMapper.newInstance(Holiday.class);
        // 创建了一个RowMapper，它是用来将每一行结果集映射到一个Java对象上的工具。
        // 这里使用的是BeanPropertyRowMapper，这是Spring提供的一个便捷的RowMapper实现，
        // 它可以自动将结果集的列映射到具有相同名称或相似名称属性的JavaBean上。
        // newInstance(Holiday.class)告诉BeanPropertyRowMapper要映射的目标对象类型是Holiday类
        return jdbcTemplate.query(sql, rowMapper);
        // 这个方法返回一个Holiday对象的列表，这个列表包含了数据库中所有假期的记录
    }

     */
}
