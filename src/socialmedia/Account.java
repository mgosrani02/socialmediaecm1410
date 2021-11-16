package socialmedia;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *  The Account class represents an account within the SocialMedia structure, with a handle and optional description
 *  @author 700040999
 *  @author 700005222
 *  @version 13/04/2021
 */
public class Account implements Serializable {
    private static int IDCounter = 0;
    private String handle;
    private String description;
    private final ArrayList<Post> posts = new ArrayList<>();
    private int numEndorsement = 0;
    private final int IDNumber;

    /**
     * Constructor for Account
     * @param username String, handle for the account
     */
    public Account(String username){
        handle = username;
        description = "No description given for user";
        IDCounter++;
        IDNumber = IDCounter;
    }

    /**
     * Constructor for Account
     * @param username String, handle for the account
     * @param dScript String, description for the account
     */
    public Account(String username, String dScript){
        handle = username;
        description = dScript;
        IDCounter++;
        IDNumber = IDCounter;
    }

    /**
     * Gets the ID Number for this instance of an Account
     * @return int, ID number
     */
    public int getIDNumber() {
        return IDNumber;
    }

    /**
     * Gets the description of this Account
     * @return String, description of this Account
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the handle of this Account
     * @return String, handle of this Account
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the description of this Account
     * @param description String, the description update
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the handle of this Account
     * @param handle String, the handle update
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Adds to the number of endorsements this Account has
     */
    public void addEndorsement(){
        numEndorsement++;
    }

    /**
     * Subtracts from the number of endorsements this Account has
     */
    public void removeEndorsement(){numEndorsement--;}

    /**
     * Adds a post to the ArrayList of this Account's Posts
     * @param p Post, the post to be added
     */
    public void addPost(Post p){
        posts.add(p);
    }

    /**
     * Removes a post from the ArrayList of this Account's Posts
     * @param p Post, the post to be removed
     */
    public void deletePost(Post p){
        posts.remove(p);
    }

    /**
     * Gets the number of endorsements this Account has over all posts
     * @return int, number of endorsements total
     */
    public int getNumEndorsement() {
        return numEndorsement;
    }

    /**
     *
     * Gets the ArrayList of Posts in this Account
     * @return ArrayList&lt;Post&gt;, the ArrayList of Posts associated with this account
     */
    public ArrayList<Post> getPosts() {
        return posts;
    }

    /**
     * toString of this Account, formats the Account
     * @return String, the toString of this Account
     */
    public String toString(){
        String retString = "\nID: " + IDNumber + "\nHandle: " + handle;
        retString += "\nDescription: " + description + "\nPost Count: " + posts.size() + "\nEndorsement Count: " + numEndorsement;
        return retString;
    }
    public void resetCounter(){
        IDCounter =0;
    }
}
