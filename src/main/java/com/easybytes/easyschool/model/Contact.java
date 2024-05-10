package com.easybytes.easyschool.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

@Data // 这是一个Lombok注解，自动为类生成了大量的模板代码，比如getters、setters、toString()、equals()和hashCode()方法。
@Entity // 这是一个JPA注解，指定这个类是一个实体。这意味着它是一个由JPA管理的实体，对应于数据库中的一个表。
@Table(name="contact_msg") // 此注解指定了实体映射到数据库中的表名。在这个例子中，表名为contact_msg。
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "SqlResultSetMapping.count", columns = @ColumnResult(name = "cnt"))
})
@NamedQueries({
        @NamedQuery(name = "Contact.findOpenMsgs",
        query = "select c from Contact c where c.status = :status"),
        @NamedQuery(name = "Contact.updateMsgStatus",
        query = "update Contact c set c.status = ?1 where c.contactId = ?2")
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "Contact.findOpenMsgsNative",
                query = "SELECT * FROM contact_msg c WHERE c.status = :status"
                ,resultClass = Contact.class),
        @NamedNativeQuery(name = "Contact.findOpenMsgsNative.count",
                query = "select count(*) as cnt from contact_msg c where c.status = :status",
                resultSetMapping = "SqlResultSetMapping.count"),
        /*Spring Data JPA doesn’t support dynamic sorting for native queries.
        Doing that would require Spring Data to analyze the provided statement and generate
        the ORDER BY clause in the database-specific dialect. This would be a very complex operation
        and is currently not supported by Spring Data JPA.*/
        @NamedNativeQuery(name = "Contact.updateMsgStatusNative",
                query = "UPDATE contact_msg c SET c.status = ?1 WHERE c.contact_id = ?2")
})
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    // 这个注解是Hibernate特有的，不是JPA标准的一部分。它允许您定义一个自定义的ID生成器。
    // 在这个例子中，name = "native"定义了生成器的名字，strategy = "native"指定了使用数据库的本地策略来生成主键。
    // 这意味着Hibernate将根据数据库的类型自动选择最合适的方式（比如，在MySQL中使用自增长，在PostgreSQL中使用序列等）
    @Column(name = "contact_id")
    private int contactId;
    /*
    * @NotNull: Checks if a given field is not null but allows empty values & zero elements inside collections.
      @NotEmpty: Checks if a given field is not null and its size/length is greater than zero.
      @NotBlank: Checks if a given field is not null and trimmed length is greater than zero.
    * */
    @NotBlank(message="Name must not be blank")
    @Size(min=3, message="Name must be at least 3 characters long")
    private String name;

    @NotBlank(message="Mobile number must not be blank")
    @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
    @Column(name = "mobile_num")
//  regexp="(^$|[0-9]{10})"定义了正则表达式：
//  ^$匹配空字符串。这里允许空字符串是因为@NotBlank已经确保了字段不会是空的，这部分主要是为了正则表达式本身的完整性，允许在其他上下文中可能的空值情况。
//  [0-9]{10}确保字符串由10位数字组成。这里的[0-9]表示任何单个数字，{10}说明这个模式重复10次，即字符串长度为10的数字。
    private String mobileNum;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Subject must not be blank")
    @Size(min=5, message="Subject must be at least 5 characters long")
    private String subject;

    @NotBlank(message="Message must not be blank")
    @Size(min=10, message="Message must be at least 10 characters long")
    private String message;

    private String status;
    /* example 20 之后使用@Data
    private String name;
    private String mobileNum;
    private String email;
    private String subject;
    private String message;
     */

    /* example 20之前，不使用Lambok，需要自己完成getter setter constructor 等
    public Contact() {
    }

    public String getName() {
        return this.name;
    }

    public String getMobileNum() {
        return this.mobileNum;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSubject() {
        return this.subject;
    }

    public String getMessage() {
        return this.message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNum(String mobileNum) {
        this.mobileNum = mobileNum;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Contact)) return false;
        final Contact other = (Contact) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$mobileNum = this.getMobileNum();
        final Object other$mobileNum = other.getMobileNum();
        if (this$mobileNum == null ? other$mobileNum != null : !this$mobileNum.equals(other$mobileNum)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final Object this$subject = this.getSubject();
        final Object other$subject = other.getSubject();
        if (this$subject == null ? other$subject != null : !this$subject.equals(other$subject)) return false;
        final Object this$message = this.getMessage();
        final Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Contact;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $mobileNum = this.getMobileNum();
        result = result * PRIME + ($mobileNum == null ? 43 : $mobileNum.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $subject = this.getSubject();
        result = result * PRIME + ($subject == null ? 43 : $subject.hashCode());
        final Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        return result;
    }

    public String toString() {
        return "Contact(name=" + this.getName() + ", mobileNum=" + this.getMobileNum() + ", email=" + this.getEmail() + ", subject=" + this.getSubject() + ", message=" + this.getMessage() + ")";
    }

     */
}
