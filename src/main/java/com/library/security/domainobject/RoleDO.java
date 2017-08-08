package com.library.security.domainobject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.library.security.domainvalue.RolesEnum;

@Entity
@Table(name = "roles")
public class RoleDO
{
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "role cant be null !")
    private RolesEnum role;
    
    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserDO user;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public RolesEnum getRole()
    {
        return role;
    }

    public void setRole(RolesEnum role)
    {
        this.role = role;
    }

    public UserDO getUser()
    {
        return user;
    }

    public void setUser(UserDO user)
    {
        this.user = user;
    }
    
    

}
