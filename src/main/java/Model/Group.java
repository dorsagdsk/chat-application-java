package Model;

import java.util.ArrayList;

public class Group {

    private String groupName;
    private ArrayList<Person> members;
    private int id;
    private String link;
    private Person admin;

    public Group(int id , Person admin , String groupName , String link , ArrayList<Person> members){
        this.id=id;
        this.members=members;
        this.admin=admin;
        this.groupName=groupName;
        this.link=link;
    }

    public Group(int id , Person admin , String groupName , String link ){
        this.id=id;
        this.admin=admin;
        this.groupName=groupName;
        this.link=link;
        members=new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<Person> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Person> members) {
        this.members = members;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Person getAdmin() {
        return admin;
    }

    public void setAdmin(Person admin) {
        this.admin = admin;
    }

    public void setMembers(Person member){
        members.add(member);
    }
}
