package Model;

import Model.Group;
import Model.Message;
import Model.UserFile;

import java.io.*;
import java.util.ArrayList;

public class GroupsMessageFile {

    private UserFile userFile;

    public void addMessage(String message , String sender , int groupId , String dateTime){  // add kardan message sender and time and id group to file
        File file = new File("messageGroups.txt");
        try (BufferedWriter writer = new BufferedWriter( new FileWriter(file,true))){

            writer.newLine();
            writer.write(sender+"-"+groupId+"-"+message+"-"+dateTime);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public int getFileSize(){  // return the size of file
        int size=0;
        File file=new File("messageGroups.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            while (reader.readLine() != null){
                size++;
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return size;
    }

    public ArrayList<Message> getAllMessages(){
        int size = getFileSize();
        ArrayList<Message> messages= new ArrayList<>();
        userFile= new UserFile();
        File file = new File("messageGroups.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                String line = reader.readLine();               // put the line in String
                String[] lineContent = line.split("-"); // khandan khotot file v rikhtan an ha dar arry list messges
                messages.add(new Message( lineContent[0] ,Integer.parseInt(lineContent[1]) ,lineContent[2],
                        lineContent[3]));
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public ArrayList<Message> getGroupMessages( int groupId){  // find all the message of the one group
        ArrayList<Message> allMessage =new ArrayList<>(); // contain all message
        allMessage.addAll(getAllMessages());
        ArrayList<Message> userMessages= new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            if(allMessage.get(i).getGroupId() == groupId){
                userMessages.add(new Message(allMessage.get(i).getSender().getUsername(),groupId,
                        allMessage.get(i).getMessage() , allMessage.get(i).getDateTime()));
            }
        }
        return userMessages;
    }

    public void clearHistory(Group group){  // ba ravash nadida graftan message ha group entkhab shoda pak mishan
        ArrayList<Message> allMessage = getAllMessages();
        ArrayList<Message> messagesAfterClearHistory = new ArrayList<>();
        for(int i=0 ; i < allMessage.size() ; i++){
            if(allMessage.get(i).getGroupId() != group.getId()){
                messagesAfterClearHistory.add(allMessage.get(i));
            }
        }
        writeInFileAfterChange(messagesAfterClearHistory);
    }

    public void writeInFileAfterChange(ArrayList<Message> messages){  // navashtan file message group az aval
        File file = new File("messageGroups.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){

                for(int i=0 ; i < messages.size() ; i++){
                    writer.newLine();
                    writer.write(messages.get(i).getSenderUsername()+"-"+messages.get(i).getGroupId()+"-"
                    +messages.get(i).getMessage()+"-"+messages.get(i).getDateTime());
                }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void editMessage(Message selectedMessage , Message editedMessage){
        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.addAll(getAllMessages());
        ArrayList<Message> messagesAfterEditing= new ArrayList<>();
        for(int i=0 ; i < allMessages.size() ; i++){
            boolean condition = (allMessages.get(i).getMessage().equals(selectedMessage.getMessage()) &&
                    allMessages.get(i).getSender().getId()==selectedMessage.getSender().getId() &&
                    allMessages.get(i).getDateTime().equals(selectedMessage.getDateTime()));
            if(condition){
                messagesAfterEditing.add(editedMessage);
            }
            else
                messagesAfterEditing.add(allMessages.get(i));
        }

        writeInFileAfterChange(messagesAfterEditing);
    }

    public void deleteMessage(Message selectedMessage){ // ba ravash nadid graftan message select shoda pak mishavad
        ArrayList<Message> allMessages = new ArrayList<>();
        allMessages.addAll(getAllMessages());
        ArrayList<Message> messagesAfterDeleting = new ArrayList<>();
        for(int i=0 ; i < allMessages.size() ; i++){
            boolean condition = (allMessages.get(i).getMessage().equals(selectedMessage.getMessage()) &&
                    allMessages.get(i).getSender().getId()==selectedMessage.getSender().getId() &&
                    allMessages.get(i).getDateTime().equals(selectedMessage.getDateTime()));
            if(!condition){                              // agar brabar nbashad add ba arry list mishavad
                messagesAfterDeleting.add(allMessages.get(i));
            }
        }

        writeInFileAfterChange(messagesAfterDeleting);
    }

    public void deleteLastMessage(){
        int size = getFileSize();
        File file = new File("messageGroups.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i< size-1 ; i++){ // khandan file ta (size-1 )gabl az akharin message
                reader.readLine();
            }
            String[] line = reader.readLine().split("-");
            deleteMessage(new Message(line[0],Integer.parseInt(line[1]),line[2],line[3]));

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

}
