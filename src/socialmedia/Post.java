package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Post class represents a post within the SocialMedia structure. Created for ECM1410 CA3.
 * Two subclasses, Endorsement and Comment
 * @author 700040999
 * @author 700005222
 * @version 13/04/2021
 */
public class Post implements Serializable {
    private static int postIDCounter = 0;
    private String message;
    private Account owner;
    private final ArrayList<Comment> comments = new ArrayList<>();
    private final ArrayList<Endorsement> endorsements = new ArrayList<>();
    private boolean isEmpty = false;
    private int postID;
    private int numTabs;

    /**
     * Constructor for Post
     * @param o the owner account
     * @param m the post's message
     */
    public Post(Account o, String m){
        owner = o;
        message = m;
        postIDCounter++;
        postID = postIDCounter;
    }

    /**
     * Constructor for Post
     * @param o the owner account
     */
    public Post(Account o){
        owner = o;
        postIDCounter++;
        postID = postIDCounter;
    }

    /**
     * No-args constructor. Used for empty posts
     */
    public Post(){
        owner = null;
        message = "The original content was removed from the system and is no longer available.";
        isEmpty = true;

    }

    /**
     * @return boolean isEmpty, whether or not the post is an empty post
     */
    public boolean isEmpty(){
        return isEmpty;
    }

    /**
     * @param owner the new owner of this post
     */
    public void setOwner(Account owner) {
        this.owner = owner;
    }

    /**
     *Removes a post and all its endorsements.
     */
    public void deletePost(){
        message = "The original content was removed from the system and is no longer available.";
        isEmpty = true;
        for (Endorsement endorsement : endorsements) {
            endorsement.deleteEndorse();
        }
    }

    /**
     * Creates the toString of the post
     * @return toString of the Post. If it's empty, it returns the default empty message
     */
    public String toString(){
        if(!isEmpty) {
            StringBuilder retString = new StringBuilder();
            retString.append("ID: ").append(postID).append("\n");

            for (int j = 0; j < numTabs; j++) {

                retString.append("\t");
            }
            retString.append("Account: ").append(owner.getHandle()).append("\n");
            for (int j = 0; j < numTabs; j++) {
                retString.append("\t");
            }
            retString.append("No. endorsements: ").append(endorsements.size()).append(" | No. comments: ").append(getNumComments()).append("\n");
            for (int j = 0; j < numTabs; j++) {
                retString.append("\t");
            }
            retString.append(message);
            return retString.toString();
        }else{
            return "The original content was removed from the system and is no longer available.";
        }
    }

    /**
     * Gets the post's owner
     * @return Account, the owner of the post
     */
    public Account getOwner(){
        return owner;
    }

    /**
     * Gets the post's owner's handle
     * @return String, the handle of the post's owner
     */
    public String getHandle(){
        return owner.getHandle();
    }

    /**
     * Gets the post's message
     * @return String, the post's message
     */
    public String getMessage(){
        return message;
    }

    /**
     * Adds an endorsement to the Post
     * @param e Endorsement, the endorsement to add
     */
    public void addEndorsement(Endorsement e){
        endorsements.add(e);
    }

    /**
     * Gets the number of endorsements of this post
     * @return int, the number of endorsements this post has
     */
    public int getNumEndorsements(){
        return endorsements.size();
    }

    /**
     * Gets the ID number of this post
     * @return int, the ID of the post
     */
    public int getPostID(){
        return postID;
    }

    /**
     * Adds a comment to this post
     * @param c Comment, the comment to be added to the post
     */
    public void addComment(Comment c){
        comments.add(c);
    }

    /**
     * Gets the number of comments on this Post
     * @return int, number of comments
     */
    public int getNumComments(){
        int counter = 0;
        for (Comment comment : comments) {
            if (!comment.isEmpty()) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Constructs a StringBuilder with a tree structure for each comment
     * @param n int, number of tabs to increment
     * @return StringBuilder, a StringBuilder of the tree comment structure
     */
    public StringBuilder allComments(int n){
        StringBuilder retString = new StringBuilder();

        numTabs = n;
        n++;
        retString.append(this);

        retString.append("\n");
        for (Comment comment : comments) {
            if(!comment.isEmpty() || comment.getNumComments()!= 0){
                for (int j = 0; j < numTabs; j++) {
                    retString.append("\t");
                }
                retString.append("|\n");
                for (int j = 0; j < numTabs; j++) {
                    retString.append("\t");
                }
                retString.append("| > ");
                retString.append(comment.allComments(n));
            }
        }
        return retString;
    }
    public void resetCounter(){
        postIDCounter = 0;
    }
}
