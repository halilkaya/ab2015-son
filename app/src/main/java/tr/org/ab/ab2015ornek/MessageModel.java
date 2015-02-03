package tr.org.ab.ab2015ornek;

public class MessageModel {

    private String date_sent;
    private String message;
    private String name;

    public MessageModel(String date_sent, String name, String message) {
        this.date_sent = date_sent;
        this.name = name;
        this.message = message;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
