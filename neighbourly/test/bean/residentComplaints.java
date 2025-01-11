/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;
import java.time.LocalDate;

public class residentComplaints {
    
    String complaintType;
    String description;
    LocalDate date;
    String location;
    String attachment;

    public residentComplaints(String complaintType, String description, LocalDate date, String location, String attachment) {
        this.complaintType = complaintType;
        this.description = description;
        this.date = date;
        this.location = location;
        this.attachment = attachment;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    
    
   
    
}