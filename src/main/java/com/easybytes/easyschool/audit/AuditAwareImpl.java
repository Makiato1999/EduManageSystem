package com.easybytes.easyschool.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/*
@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(
                SecurityContextHolder.getContext().getAuthentication().getName()
                // 这行代码获取当前经过认证的用户的名字。这是通过Spring Security的上下文来实现的，它持有认证信息，包括当前用户的细节。
        );
        // 这将获取到的用户名包装成一个Optional对象。如果SecurityContextHolder中的Authentication对象为null，
        // 或者用户未认证，这里将返回一个空的Optional，确保了方法的返回值永远不会是null。
    }
}
// 这个实现允许你在使用Spring Data JPA时，
// 自动将当前认证用户的名字作为创建者或更新者信息填充到实体的@CreatedBy和@LastModifiedBy字段。
// 这样，每当实体被创建或更新时，你都可以轻松跟踪是哪个用户进行了操作。这对于实现审计日志、追踪数据变更历史等功能非常有用。
*/


@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}