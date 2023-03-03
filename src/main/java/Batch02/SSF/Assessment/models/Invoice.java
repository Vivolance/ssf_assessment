package Batch02.SSF.Assessment.models;

public class Invoice {

    private String invoiceId;
    private String name;
    private String address;
    private Float total;

    public Invoice() {

    }

    public Invoice(String invoiceId, String name, String address, Float total) {
        this.invoiceId = invoiceId;
        this.name = name;
        this.address = address;
        this.total = total;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }
}