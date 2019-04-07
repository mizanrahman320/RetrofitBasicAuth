package pjl.jeans.retrofitbasicauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {
    @SerializedName("Gender")
    @Expose
    private String Gender;

    @SerializedName("Designation")
    @Expose
    private String Designation;

    @SerializedName("Name")
    @Expose
    private String Name;

    /**
     * @return The Version_Name
     */
    public String getGender() {
        return Gender;
    }

    /**
     * @param Gender The Version_Name
     */
    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    /**
     * @return The Version_No
     */
    public String getDesignation() {
        return Designation;
    }

    /**
     * @param Designation The Version_No
     */
    public void setDesignation(String Designation) {
        this.Designation = Designation;
    }

    /**
     * @return The email
     */
    public String getName() {
        return Name;
    }

    /**
     * @param Name The email
     */
    public void setName(String Name) {
        this.Name = Name;
    }


}
