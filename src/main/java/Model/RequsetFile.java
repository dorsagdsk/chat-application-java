package Model;

import java.io.*;
import java.util.ArrayList;

public class RequsetFile {


    public void sendRequest(String from , String to){   // username fard requst dahanda _ username fard rquest giranda
        File file= new File("requests.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))){

            writer.newLine();
            writer.write(from+"-"+to);


        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean requestIsNotExist(String from , String to){ // u have a request so u cant send request.
        int size= getFileSize();
        File file= new File("requests.txt");
        boolean isNotExist=true;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size; i++){
                String line= reader.readLine();
                String[] usernames= line.split("-");
                // agar a user be digari request dahad digari ya bayad ghabool konad ya rad konad(nemitavand be soorat
                // moteghabel be an request dahad)
                if( usernames[0].equals(from) && usernames[1].equals(to)){
                    isNotExist=false;
                }
                else if( usernames[1].equals(from) && usernames[0].equals(to)){
                    isNotExist=false;
                }

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return isNotExist;
    }

    public int getFileSize(){
        int size=0;
        File file= new File("requests.txt");
        try (BufferedReader reader= new BufferedReader(new FileReader(file))){

            reader.readLine();
            while (reader.readLine() != null){
                size++;
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return size;
    }

    public ArrayList<String> getAllRequests(){  // khandan kol mohtvat file v reikhtan an dar arry
        int size=getFileSize();
        ArrayList<String> requests= new ArrayList<>();
        File file=new File("requests.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                requests.add(reader.readLine());
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return requests;
    }

    public void acceptOrRejectRequest(String from , String to){ // bad az in k accept mikon ya reject bayat hazf basha to file
        ArrayList<String> allRequests= getAllRequests();           // az ravsh nadid graftan an ra hazf mikonim
        ArrayList<String> requestsAfterEditing=new ArrayList<>();
        for( int i=0 ; i < allRequests.size() ; i++){

            String[] request= allRequests.get(i).split("-");
            if( !(from.equals(request[0]) && to.equals(request[1]) )){
                requestsAfterEditing.add(allRequests.get(i));
            }
        }
        writeAllInFile(requestsAfterEditing);
    }

    public void writeAllInFile(ArrayList<String> requests){

        File file= new File("requests.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))){


            for(int i=0 ; i < requests.size() ; i++){
                writer.newLine();
                writer.write(requests.get(i));
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getUserAllRequests(String username){  // to show all request(username) in table -->show request
        ArrayList<String> allRequests= new ArrayList<>();
        allRequests.addAll(getAllRequests());
        ArrayList<String> userAllRequests= new ArrayList<>();
        for(int i=0 ; i < allRequests.size() ; i++){
            String[] users= allRequests.get(i).split("-");
            if(users[1].equals(username)){
                userAllRequests.add(users[0]);
            }
        }
        return userAllRequests;
    }
}
