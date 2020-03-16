package org.uma.cloud.batch;


import lombok.Data;

@Data
public class Usage {

    private Long id;

    private String firstName;

    private String lastName;

    private Long minutes;

    private Long dataUsage;


    public Usage(Long id, String firstName, String lastName, Long minutes, Long dataUsage) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.minutes = minutes;
        this.dataUsage = dataUsage;
    }

    public Usage() {
    }

}
