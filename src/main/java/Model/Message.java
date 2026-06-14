package Model;

public class Message {

    private Person sender;
    private String message;
    private String senderUsername;
    private UserFile userFile;
    private String receiverUsername;
    private String dateTime;
    private int groupId;

    public Message(String senderUsername, String receiverUsername , String message , String dateTime) {
        userFile= new UserFile();
        this.sender = userFile.getUser(senderUsername);
        this.message = message;
        this.dateTime=dateTime;
        this.receiverUsername=receiverUsername;
        this.senderUsername=senderUsername;
    }

    public Message(String senderUsername , int groupId , String message , String dateTime){
        userFile=new UserFile();
        this.senderUsername=senderUsername;
        this.sender=userFile.getUser(senderUsername);
        this.groupId=groupId;
        this.message=message;
        this.dateTime=dateTime;
    }

    public int getGroupId(){
        return groupId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
