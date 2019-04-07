package pjl.jeans.retrofitbasicauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Android_Version {
    @SerializedName("Version Name")
    @Expose
    private String Version_Name;

    @SerializedName("Version_No")
    @Expose
    private String Version_No;

    @SerializedName("API_Level")
    @Expose
    private String API_Level;

    /**
     * @return The Version_Name
     */
    public String getVersion_Name() {
        return Version_Name;
    }

    /**
     * @param Version_Name The Version_Name
     */
    public void setVersion_Name(String Version_Name) {
        this.Version_Name = Version_Name;
    }

    /**
     * @return The Version_No
     */
    public String getVersion_No() {
        return Version_No;
    }

    /**
     * @param Version_No The Version_No
     */
    public void setVersion_No(String Version_No) {
        this.Version_No = Version_No;
    }

    /**
     * @return The email
     */
    public String getAPI_Level() {
        return API_Level;
    }

    /**
     * @param API_Level The email
     */
    public void setAPI_Level(String API_Level) {
        this.API_Level = API_Level;
    }


}
