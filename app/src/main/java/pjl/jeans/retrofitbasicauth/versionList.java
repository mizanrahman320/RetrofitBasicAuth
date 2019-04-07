package pjl.jeans.retrofitbasicauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class versionList {
    @SerializedName("Android Version List")
    @Expose
    private ArrayList<Android_Version> versions = new ArrayList<>();

    /**
     * @return The contacts
     */
    public ArrayList<Android_Version> getVersion() {
        return versions;
    }

    /**
     * @param versions The contacts
     */
    public void setVersion(ArrayList<Android_Version> versions) {
        this.versions = versions;
    }
}
