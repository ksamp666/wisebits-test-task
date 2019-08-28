package Enums;

public enum TestCustomersEnum {
    TEST_CUSTOMER_1("Pinguins", "Anatoliy Zagrebaylo", "Africa", "NDjamena", "777777", "Chad"),
    TEST_CUSTOMER_2("Fishes", "Vadim Petrunko", "Asia", "Pekin", "111111", "China");

    private String customerName, contactName, address, city, postalCode, country;

    TestCustomersEnum(String customerName, String contactName, String address, String city, String postalCode, String country) {
        this.customerName = customerName;
        this.contactName = contactName;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getContactName() {
        return contactName;
    }

    public String getCountry() {
        return country;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
