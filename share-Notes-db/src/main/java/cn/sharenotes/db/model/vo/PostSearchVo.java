package cn.sharenotes.db.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 76905
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchVo implements Serializable {

    private Integer id;

    private String createFrom;

    private String avatar;

    private String title;

    private Date updateTime;

    private String cateName;

}
