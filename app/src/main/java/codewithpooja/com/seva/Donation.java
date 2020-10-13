package codewithpooja.com.seva;

import java.io.Serializable;

public class Donation implements Serializable {

    String donation_quantity;
    String donation_brief;
    String image_name;
    String donation_date;
    String name;
    String contact;
    String donation_id;

    public Donation(String donation_quantity, String donation_brief, String image_name, String donation_date, String name, String contact,String donation_id){

        this.donation_quantity = donation_quantity;
        this.donation_brief = donation_brief;
        this.image_name = image_name;
        this.donation_date = donation_date;
        this.name = name;
        this.contact = contact;
        this.donation_id = donation_id;
    }

    public String getDonation_quantity() {
        return donation_quantity;
    }

    public String getDonation_date() {
        return donation_date;
    }

    public String getDonation_id() {
        return donation_id;
    }

    public String getName() {
        return name;
    }
}
