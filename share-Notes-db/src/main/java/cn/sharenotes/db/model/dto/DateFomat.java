package cn.sharenotes.db.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kiwi
 * @date 2019/10/18 19:11
 */

public class DateFomat {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    public DateFomat() {
    }

    public void today(){
        this.date=new Date();
    }
    public void yesterday(){
        Date datet = new Date();
        this.date=new Date(datet.getTime()- 24*3600*1000L);
    }
    public void tdbYesterday(){
        Date datet = new Date();
        this.date=new Date(datet.getTime()- 2*24*3600*1000L);
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
