package cn.sharenotes.wxapi.system.entity;

import cn.sharenotes.wxapi.system.entity.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.annotations.ConstructorArgs;

import javax.persistence.*;

/**
 * @author kiwi
 * @date 2019/11/24 11:24
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "system_const")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemConst extends BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "is_Init")
    private String isInit;

    @Basic
    @Column(name = "const_Name")
    private String constName;

}
