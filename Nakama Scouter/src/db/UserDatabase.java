package db;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a list of every user of the applications, and can handle adding new users to the database
 * and editting the data of preexisting users.
 * Last modified 04/12/2021 by Edward Hicks
 * @author Edward Hicks
 */
public class UserDatabase {
    protected List<UserProfile> applicationUser;
    private final int minimumUsernameLength = 4;
    private final int minimumPasswordLength = 8;
    private final int minimumAge = 18;

    public UserDatabase() {
        this.applicationUser = new ArrayList<UserProfile>();
    }

    /**
     * This methods checks to see if the username of the new account is unique and if it is, creates a new account using the
     *     provided username and password and adds it to the applicationUser List. The applicationUser list is then sorted.
     * @param _userName
     * @param _email
     * @param _passWord
     * @param _age
     * @param _city
     * @param _ipAddress
     */
    public void addNewApplicationUser(String _userName, String _email, String _passWord, int _age, String _city, String _ipAddress) {
        //Create new user and add it to the List
        if (checkForValidUsername(_userName) && checkForValidPassword(_passWord) && checkForValidAge(_age) && checkForNoSemicolons(_userName, _passWord, _email, _city, _ipAddress)) {
            UserProfile newUser = new UserProfile(_userName, _email, _passWord, _age, _city, _ipAddress);
            applicationUser.add(newUser);
            sortList();
            System.out.println("New account created! Username: " + _userName + ", Password: " + _passWord);
        }
        else System.out.println("Account registration denied.");
    }

    /**
     * The username is checked to see if it is compliant with various username requirements, then is checked for duplicates among
     * pre-existing users.
     * @param _userName
     * @return
     */
    public boolean checkForValidUsername(String _userName) {
        boolean usernameIsCompliant = true;
        //Checks the username to see if it meets the minimum password length
        if (_userName.length() < minimumUsernameLength) {
            System.out.println("Username must be at least " + minimumUsernameLength + " characters in length.");
            usernameIsCompliant = false;
        }
        //Checks the username to ensure it has no spaces
        if (_userName.contains(" ")) {
            System.out.println("Username must not contain any spaces.");
            usernameIsCompliant = false;
        }
        if (usernameIsCompliant) return checkUsernameForDuplicates(_userName);
        return false;
    }

    /**
     * The applicationUser database is searched to see if the desired username doesn't already exist, returning the outcome.
     * @param _userName
     * @return
     */
    public boolean checkUsernameForDuplicates(String _userName) {
        //First, check if database is empty. If so, return true.
        if (this.applicationUser.size() == 0) return true;

        int lowerThreshold = 0;
        int upperThreshold = this.applicationUser.size()-1;

        for (int i = upperThreshold / 2; ; i = lowerThreshold + ((upperThreshold-lowerThreshold)/2)) {
            int usernameCompare = compareWithCase(_userName, getUsernameByIndex(i));
            //A matching username has been found, new username is not unique
            if (usernameCompare == 0) {
                System.out.println("The username " + _userName + " is already being used by another account.");
                return false;
            }
            //A matching username has not been found, new username is unique
            else if (lowerThreshold == upperThreshold) return true;
                //Index is too high, move upperThreshold down
            else if (usernameCompare < 0) upperThreshold = i;
                //Index is too low, move lowerIndex up
            else {
                //If the thresholds are 1 away from each other, move lowerThreshold up to prevent infinite loop
                if (upperThreshold-lowerThreshold == 1) lowerThreshold = upperThreshold;
                else lowerThreshold = i;
            }
        }
    }

    /**
     * The password provided is checked to see if it is compliant with various password requirements.
     * @param _passWord
     * @return
     */
    public boolean checkForValidPassword(String _passWord) {
        boolean passwordIsCompliant = true;

        //Checks the password to see if meets the minimum password length
        if (_passWord.length() < minimumPasswordLength) {
            System.out.println("Password must be at least " + minimumPasswordLength + " characters in length.");
            passwordIsCompliant = false;
        }
        //Checks the password to ensure it has no spaces
        if (_passWord.contains(" ")) {
            System.out.println("Password must not contain any spaces.");
            passwordIsCompliant = false;
        }
        //Checks the password to ensure it contains both uppercase and lowercase letters
        if (_passWord.equals(_passWord.toUpperCase()) || _passWord.equals(_passWord.toLowerCase())) {
            System.out.println("Password must contain both uppercase and lowercase letters.");
            passwordIsCompliant = false;
        }
        //Checks the password to ensure it contains at least one number
        if (!_passWord.contains("0") && !_passWord.contains("1") &&!_passWord.contains("2") &&!_passWord.contains("3") &&!_passWord.contains("4") &&!_passWord.contains("5") &&!_passWord.contains("6") &&!_passWord.contains("7") &&!_passWord.contains("8") &&!_passWord.contains("9")) {
            System.out.println("Password must contain at least one number.");
            passwordIsCompliant = false;
        }
        return passwordIsCompliant;
    }

    /**
     * The age is checked to see if it satisfies the minimum age requirement
     * @param _age
     * @return
     */
    public boolean checkForValidAge(int _age) {
        if (_age < 18) {
            System.out.println("Users must be over the age of " + minimumAge + " to use this service.");
            return false;
        }
        return true;
    }

    /**
     * All String information is checked to ensure it doesn't contain a semicolon that would break how the database is saved and loaded.
     * @param _userName
     * @param _passWord
     * @param _email
     * @param _city
     * @param _ipAddress
     * @return
     */
    public boolean checkForNoSemicolons(String _userName, String _passWord, String _email, String _city, String _ipAddress) {
        boolean noSemicolons = true;
        if (_userName.contains(";")) {
            System.out.println("Username must not contain a semicolon.");
            noSemicolons = false;
        }
        if (_passWord.contains(";")) {
            System.out.println("Password must not contain a semicolon.");
            noSemicolons = false;
        }
        if (_email.contains(";")) {
            System.out.println("Email address must not contain a semicolon.");
            noSemicolons = false;
        }
        if (_city.contains(";")) {
            System.out.println("City must not contain a semicolon.");
            noSemicolons = false;
        }
        if (_ipAddress.contains(";")) {
            System.out.println("IP address must not contain a semicolon.");
            noSemicolons = false;
        }
        return noSemicolons;
    }

    /**
     * A single new user is added to the database, sorted so that it's in alphabetical order.
     *     NOTE: THIS METHOD SHOULD BE CALLED IMMEDIATELY AFTER A NEW ACCOUNT IS CREATED
     */
    public void sortList() {
        if (this.applicationUser.size() > 1) {
            UserProfile newestUser = this.applicationUser.get(this.applicationUser.size()-1);
            String newestUsername = newestUser.getUsername();
            int lowerSortThreshold = 0;
            int upperSortThreshold = this.applicationUser.size()-2;
            int index = upperSortThreshold / 2;
            //Find the appropriate index for the new account. Loop will continue until the newest username comes after the username at i-1 and before the username at i
            for (; ; index = lowerSortThreshold + ((upperSortThreshold-lowerSortThreshold)/2)) {
                //If the new username should come before i...
                if (compareWithCase(newestUsername, getUsernameByIndex(index)) < 0) {
                    //...and it's already at the start, it belongs at the beginning.
                    if (index == 0) break;
                    //Otherwise, lower the upperSortThreshold.
                    else upperSortThreshold = index;
                }
                //If the newest username should come after i AND before i+1 (or it IS i+1 ie it's at the end), shift i up and break loop
                else if (compareWithCase(newestUsername, getUsernameByIndex(index+1)) <= 0) {
                    index++;
                    break;
                }
                //The newest username comes after i and i+1, raise lowerSortThreshold
                else lowerSortThreshold = index;
            }
            //Shift every account "greater than" the new account forward one space
            for (int j = this.applicationUser.size()-1; j > index; j--) setUserProfileByIndex(j, getUserProfileByIndex(j-1));
            //Set the new account to the correct index
            this.applicationUser.set(index, newestUser);
        }
    }

    /**
     * Compares two strings with a uniform case.
     * @param _string1
     * @param _string2
     * @return
     */
    public int compareWithCase(String _string1, String _string2) { return _string1.toLowerCase().compareTo(_string2.toLowerCase()); }

    /**
     * Converts the personal information of all users into a String.
     * @return
     */
    public String printDatabase() {
        //Returns nothing if the method is called while the database is empty
        if(this.getSize() == 0) return "";

        String allUserData = "";
        //This loop gets the data of every user in order and adds them to a single String.
        for (int i = 0; i < this.getSize(); i++) {
            allUserData += this.printUser(i) + "\n";
        }
        //Trims off the last \n in the String.
        allUserData = allUserData.substring(0, allUserData.length()-2);
        return allUserData;
    }

    /**
     * Converts the personal information of a single user into a String, returning the outcome.
     * @param i
     * @return
     */
    public String printUser(int i) {
        try {
            String userData = "";
            userData += this.getUsernameByIndex(i) + ";";
            userData += this.getPasswordByIndex(i) + ";";
            userData += this.getEmailByIndex(i) + ";";
            userData += this.getAgeByIndex(i) + ";";
            userData += this.getCityByIndex(i) + ";";
            userData += this.getIpAddressByIndex(i) + ";";
            return userData;
        } catch (Exception ex) {
            //Only happens if i exceeds the scope of the database or is less than 0 (Hopefully)
            return "";
        }
    }

    //=================  GETTERS =================
    public UserProfile getUserProfileByIndex(int _i) { return this.applicationUser.get(_i); }
    public String getUsernameByIndex(int _i) { return this.applicationUser.get(_i).getUsername(); }
    public String getEmailByIndex(int _i) { return this.applicationUser.get(_i).getEmail(); }
    public String getPasswordByIndex(int _i) { return this.applicationUser.get(_i).getPassword(); }
    public int getAgeByIndex(int _i) { return this.applicationUser.get(_i).getAge(); }
    public String getCityByIndex(int _i) { return this.applicationUser.get(_i).getCity(); }
    public String getIpAddressByIndex(int _i) { return this.applicationUser.get(_i).getIpAddress(); }
    public int getSize() { return this.applicationUser.size(); }

    //=================  SETTERS =================
    public void setUserProfileByIndex(int _i, UserProfile _userName) { this.applicationUser.set(_i, _userName); }
    public void setUsernameByIndex(int _i, String _userName) {
        UserProfile edittedUser = getUserProfileByIndex(_i);
        edittedUser.setUsername(_userName);
        setUserProfileByIndex(_i, edittedUser);
    }
    public void setEmailByIndex(int _i, String _email) {
        UserProfile edittedUser = getUserProfileByIndex(_i);
        edittedUser.setEmail(_email);
        setUserProfileByIndex(_i, edittedUser);
    }
    public void setPasswordByIndex(int _i, String _passWord) {
        UserProfile edittedUser = getUserProfileByIndex(_i);
        edittedUser.setPassword(_passWord);
        setUserProfileByIndex(_i, edittedUser);
    }
    public void setBirthdayByIndex(int _i, int _age) {
        UserProfile edittedUser = getUserProfileByIndex(_i);
        edittedUser.setAge(_age);
        setUserProfileByIndex(_i, edittedUser);
    }
    public void setCityByIndex(int _i, String _city) {
        UserProfile edittedUser = getUserProfileByIndex(_i);
        edittedUser.setCity(_city);
        setUserProfileByIndex(_i, edittedUser);
    }
    public void setIpAddressByIndex(int _i, String _ipAddress) {
        UserProfile edittedUser = getUserProfileByIndex(_i);
        edittedUser.setIpAddress(_ipAddress);
        setUserProfileByIndex(_i, edittedUser);
    }
}