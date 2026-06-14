package Model;

import Controller.RegisterController;
import Model.Person;

import java.io.*;
import java.util.ArrayList;

public class UserFile {

    private File file;
    private int userId=0;
    private RegisterController regesterController;

    public void addUser(Person user){  // namashtan info of user in file
        file= new File("users.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))){
            userId= getUserId();
            writer.newLine();
            writer.write(String.valueOf(userId));
            writer.newLine();
            writer.write(user.getName());
            writer.newLine();
            writer.write(user.getLastname());
            writer.newLine();
            writer.write(user.getUsername());
            writer.newLine();
            writer.write(user.getPassword());
            writer.newLine();

            writer.write(user.getEmail());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public int getUserId(){   //
        int size= getFileSize();// size of the num user
        file = new File("users.txt");
        int id=0;
        for (int counter=0 ;  counter < size ; counter++){
            id++;
        }
        return (id+1);
    }

    public int getUserId(String username){  // get id of user we get get username ___> id
        int size = getFileSize();
        int id=0;
        file= new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for (int i=0 ; i < size ; i++){
                id= Integer.parseInt(reader.readLine());
                reader.readLine();
                reader.readLine();
                if(reader.readLine().equals(username)){
                    return id;
                }
                reader.readLine();
                reader.readLine();

            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return id;
    }

    public int getFileSize(){ //size of the num user
        file = new File("users.txt");
        int size=0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            reader.readLine();
            while (reader.readLine() != null){
                size++;
                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();
            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return size;
    }

    public ArrayList<Person> getAllUsers(){
        int size= getFileSize();
        file = new File("users.txt");
        ArrayList<Person> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            reader.readLine();
            for(int i=0 ; i < size ; i++){
                users.add(new Person(Integer.parseInt(reader.readLine()),reader.readLine(),reader.readLine()
                        , reader.readLine(),reader.readLine(), reader.readLine()));
            }
            return users;
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean userNotExist(Person user){  // username takrari nbashad
        int size= getFileSize();
        file = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                reader.readLine();
                reader.readLine();
                reader.readLine();
                if(reader.readLine().equals(user.getUsername())){
                    return false;
                }
                reader.readLine();
                reader.readLine();
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean userFound(String username , String password) {  // in login we check
        regesterController =new RegisterController();
        String hashedPassword = regesterController.doHashing(password);
        ArrayList<Person> users = new ArrayList<>();
        users.addAll(getAllUsers());
        for(int i=0 ; i < users.size() ; i++){
            if(users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(hashedPassword)){
                return true;
            }
        }
        return false;
    }

    public Person getUser(String username ){
        int size = getFileSize();
        int id=0;
        String name="";
        String family="";
        String email="";
        String password="";
        file = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            reader.readLine();
            for(int i=0 ; i < size ; i++){
                id = Integer.parseInt(reader.readLine());
                name= reader.readLine();
                family=reader.readLine();
                if(username.equals(reader.readLine())){
                    email= reader.readLine();
                    password=reader.readLine();
                    break;
                }
                reader.readLine();
                reader.readLine();


            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return (new Person(id,name,family,username,email,password));
    }

    public Person getUser(int id){
        int size = getFileSize();
        String name="";
        String family ="";
        String username="";
        String password="";
        String email= "";
        file = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){
            reader.readLine();
            for(int i=0 ; i < size ; i++){
                if(reader.readLine().equals(String.valueOf(id))) {
                    name = reader.readLine();
                    family = reader.readLine();
                    username = reader.readLine();
                    email = reader.readLine();
                    password = reader.readLine();
                    break;
                }

                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();

            }
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return (new Person(id,name,family,username,email,password));

    }

    public boolean emailNotExist(String email){
        int size= getFileSize();
        file = new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();
                reader.readLine();
                if(email.equals(reader.readLine())){
                    return false;
                }
                reader.readLine();
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return true;
    }
    public boolean searchUser(String username){  // send request
        int size=getFileSize();
        file= new File("users.txt");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))){

            reader.readLine();
            for(int i=0 ; i < size ; i++){
                reader.readLine();
                reader.readLine();
                reader.readLine();
                if(username.equals(reader.readLine())){
                    return true;
                }
                reader.readLine();
                reader.readLine();
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

}

