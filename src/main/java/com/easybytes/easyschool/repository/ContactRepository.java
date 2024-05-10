package com.easybytes.easyschool.repository;

import com.easybytes.easyschool.model.Contact;
import com.easybytes.easyschool.rommappers.ContactRowMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
    List<Contact> findByStatus(String status);

    // @Query("SELECT c FROM Contact c WHERE c.status = :status")
    @Query(value = "SELECT * FROM contact_msg c WHERE c.status = :status", nativeQuery = true)
    Page<Contact> findByStatus(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);







    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);

    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);
}

/*
public class ContactRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int saveContactMsg(Contact contact) {
        String sql = "INSERT INTO contact_msg (name,mobile_num,email,subject,message,status,created_at,created_by) VALUES (?,?,?,?,?,?,?,?)";
        //String sql = "INSERT INTO CONTACT_MSG (NAME,MOBILE_NUM,EMAIL,SUBJECT,MESSAGE,STATUS," +
                        //"CREATED_AT,CREATED_BY) VALUES (?,?,?,?,?,?,?,?)";
        return jdbcTemplate.update(sql, contact.getName(), contact.getMobileNum(),
                contact.getEmail(), contact.getSubject(), contact.getMessage(),
                contact.getStatus(), contact.getCreatedAt(), contact.getCreatedBy());
        // update方法适用于插入、更新和删除操作
    }

    public List<Contact> findMsgWithStatus(String status) {
        String sql = "SELECT * FROM contact_msg WHERE status = ?";
        return jdbcTemplate.query(sql, new PreparedStatementSetter() {
            // PreparedStatementSetter 是 Spring 框架中已经定义好的一个接口，不是开发者随意定义的。
            // 它属于 Spring 的 org.springframework.jdbc.core 包，专门用于设置 PreparedStatement 的参数。
            // 当你使用 JdbcTemplate 进行数据库操作时，如果需要动态地向 SQL 语句中传递参数，就可以利用 PreparedStatementSetter 接口。
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
            }
            // 这部分代码定义了一个匿名内部类，实现了PreparedStatementSetter接口, 其目的是为PreparedStatement设置参数。
            // ps.setString(1, status);这行代码将占位符?的值设置为方法参数status的值。1表示这是第一个占位符。
        }, new ContactRowMapper());
        // 这里创建了一个ContactRowMapper实例。ContactRowMapper是一个实现了RowMapper接口的类，
        // 用于将每一行结果集（ResultSet）映射到一个新的Contact对象。对于查询结果中的每一行，
        // RowMapper的mapRow方法都会被调用一次，将当前行转换为一个Contact对象。
    }

    public int updateMsgStatus(int contactID, String status, String updateBy) {
        String sql = "UPDATE contact_msg SET status = ?, updated_by =  ?, updated_at = ? WHERE contact_id = ?";
        return jdbcTemplate.update(sql, new PreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, status);
                ps.setString(2, updateBy);
                ps.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
                ps.setInt(4, contactID);
            }
        });
    }// 这个方法的返回值是一个int类型，它表示SQL语句执行后影响的行数。换句话说，这个返回值告诉你有多少条数据库记录被更新了。
    // 如果返回值是0，则没有记录被更新。这可能是因为没有找到匹配CONTACT_ID的记录。

}

 */