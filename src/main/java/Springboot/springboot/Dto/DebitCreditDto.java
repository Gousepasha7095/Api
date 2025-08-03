package Springboot.springboot.Dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DebitCreditDto {

    private Long id;
    private long key;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
