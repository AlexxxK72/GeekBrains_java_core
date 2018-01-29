package phoneBook;

public class PhoneRecord {
    private String sName;
    private String phone;

    public PhoneRecord(String sName, String phone) {
        this.sName = sName;
        this.phone = phone;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    //@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneRecord that = (PhoneRecord) o;

        if (!getsName().equals(that.getsName())) return false;
        return getPhone().equals(that.getPhone());
    }
    @Override
    public int hashCode() {
        int result = getsName().hashCode();
        result = 31 * result + getPhone().hashCode();
        return result;
    }

    @Override
    public java.lang.String toString() {
        return "PhoneRecord{" +
                "sName=" + sName +
                ", phone=" + phone +
                '}';
    }
}
