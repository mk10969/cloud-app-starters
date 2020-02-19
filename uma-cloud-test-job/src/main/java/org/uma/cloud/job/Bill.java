package org.uma.cloud.job;


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

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Long getDataUsage() {
        return this.dataUsage;
    }

    public Long getMinutes() {
        return this.minutes;
    }

    public Double getBillAmount() {
        return this.billAmount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDataUsage(Long dataUsage) {
        this.dataUsage = dataUsage;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }

    public void setBillAmount(Double billAmount) {
        this.billAmount = billAmount;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Bill)) return false;
        final Bill other = (Bill) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$dataUsage = this.getDataUsage();
        final Object other$dataUsage = other.getDataUsage();
        if (this$dataUsage == null ? other$dataUsage != null : !this$dataUsage.equals(other$dataUsage)) return false;
        final Object this$minutes = this.getMinutes();
        final Object other$minutes = other.getMinutes();
        if (this$minutes == null ? other$minutes != null : !this$minutes.equals(other$minutes)) return false;
        final Object this$billAmount = this.getBillAmount();
        final Object other$billAmount = other.getBillAmount();
        if (this$billAmount == null ? other$billAmount != null : !this$billAmount.equals(other$billAmount))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Bill;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $dataUsage = this.getDataUsage();
        result = result * PRIME + ($dataUsage == null ? 43 : $dataUsage.hashCode());
        final Object $minutes = this.getMinutes();
        result = result * PRIME + ($minutes == null ? 43 : $minutes.hashCode());
        final Object $billAmount = this.getBillAmount();
        result = result * PRIME + ($billAmount == null ? 43 : $billAmount.hashCode());
        return result;
    }

    public String toString() {
        return "Bill(id=" + this.getId() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", dataUsage=" + this.getDataUsage() + ", minutes=" + this.getMinutes() + ", billAmount=" + this.getBillAmount() + ")";
    }
}
