package cn.sharenotes.db.model.dto;

import java.util.ArrayList;
import java.util.List;

public class GroupEndDto {
    private int id;
    private char region;
    private List<GroupDto> items = new ArrayList<>();

    public GroupEndDto(int id, char region, List<GroupDto> items) {
        this.id = id;
        this.region = region;
        this.items = items;
    }

    public GroupEndDto(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public char getRegion() {
        return region;
    }

    public void setRegion(char region) {
        this.region = region;
    }

    public List<GroupDto> getItems() {
        return items;
    }

    public void setItems(List<GroupDto> items) {
        this.items = items;
    }
}
