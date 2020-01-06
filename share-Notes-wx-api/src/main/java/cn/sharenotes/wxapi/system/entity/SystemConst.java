package cn.sharenotes.wxapi.system.entity;

import cn.sharenotes.wxapi.system.entity.base.BaseModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.ibatis.annotations.ConstructorArgs;

import javax.persistence.*;

/**
 * @author kiwi
 * @date 2019/11/24 11:24
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "systemConst", schema = "sharenotes_dev", catalog = "")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemConst extends BaseModel {
    private Long id;
    //es是否建立了索引库 Y,N
    private String esInit;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name = "snowf-id", strategy = "com.kiwi.cloud.common.core.utils.SnowflakeIDGenerator")
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "esInit")
    public String getEsInit() {
        return esInit;
    }

    public void setEsInit(String esInit) {
        this.esInit = esInit;
    }


}
