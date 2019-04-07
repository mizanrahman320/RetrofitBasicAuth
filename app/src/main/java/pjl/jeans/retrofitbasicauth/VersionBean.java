package pjl.jeans.retrofitbasicauth;

public class VersionBean {
    private String menuName;
    private String notification;
    private String thumbnail;

    public VersionBean() {
    }

    public VersionBean(String menuName, String notification, String  thumbnail) {
        this.menuName = menuName;
        this.notification = notification;
        this.thumbnail = thumbnail;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}