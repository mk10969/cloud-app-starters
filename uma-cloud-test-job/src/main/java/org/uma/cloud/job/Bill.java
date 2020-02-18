package org.uma.cloud.job;


import lombok.Data;

@Data
public class Bill {

    private Long id;

    private String firstName;

    private String lastName;

    private Long dataUsage;

    private Long minutes;

    private Double billAmount;


    public Bill(Long id, String firstName, String lastName, Long dataUsage, Long minutes, Double billAmount) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dataUsage = dataUsage;
        this.minutes = minutes;
        this.billAmount = billAmount;
    }

    public Bill() {
    }

}
