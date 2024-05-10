package com.easybytes.easyschool.model;

import com.easybytes.easyschool.annotation.FieldsValueMatch;
import com.easybytes.easyschool.annotation.PasswordValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

//@Data
@Getter
@Setter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email address do not match!"
        )
})
// 如果数据库中的表名正好与实体类的名称相同（考虑到数据库是不区分大小写的，但习惯上实体名是驼峰式，表名是小写），
// 并且你没有特殊的映射需求（如指定目录或架构），那么@Table注解可以省略。
// 例如，如果你有一个Customer实体和一个名为customer的表，那么不需要@Table注解。
public class Person extends BaseEntity {
        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        private int personId;

        @NotBlank(message="Name must not be blank")
        @Size(min=3, message="Name must be at least 3 characters long")
        private String name;

        @NotBlank(message="Mobile number must not be blank")
        @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
        private String mobileNumber;

        @NotBlank(message="Email must not be blank")
        @Email(message = "Please provide a valid email address" )
        private String email;

        @NotBlank(message="Confirm Email must not be blank")
        @Email(message = "Please provide a valid confirm email address" )
        // 表示该字段不是数据库表的一部分，通常用于临时存储，比如确认密码和确认邮箱，这些值不需要持久化到数据库。
        @Transient
        private String confirmEmail;

        @NotBlank(message="Password must not be blank")
        @Size(min=5, message="Password must be at least 5 characters long")
        @PasswordValidator
        private String pwd;

        @NotBlank(message="Confirm Password must not be blank")
        @Size(min=5, message="Confirm Password must be at least 5 characters long")
        // 表示该字段不是数据库表的一部分，通常用于临时存储，比如确认密码和确认邮箱，这些值不需要持久化到数据库。
        @Transient
        private String confirmPwd;

        @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST, targetEntity = Roles.class)
        // 表示一个一对一的关系。在这个例子中，Person与Roles以及Address各有一个一对一的关系。通过@JoinColumn指定了外键列。
        @JoinColumn(name = "role_id", referencedColumnName = "roleId",nullable = false)
        private Roles roles;

        @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Address.class)
        @JoinColumn(name = "address_id", referencedColumnName = "addressId",nullable = true)
        private Address address;

        @ManyToOne(fetch = FetchType.LAZY, optional = true)
        @JoinColumn(name = "class_id", referencedColumnName = "classId", nullable = true)
        private Easyclass easyClass;

        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
        @JoinTable(name = "person_courses",
                joinColumns = {@JoinColumn(name = "person_id", referencedColumnName = "personId")},
                inverseJoinColumns = {@JoinColumn(name = "course_id", referencedColumnName = "courseId")}
        )
        private Set<Courses> courses = new HashSet<>();
}
