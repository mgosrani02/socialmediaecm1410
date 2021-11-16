package socialmedia;

/**
 * The Comment class represents a Comment on a Post. A Comment has a parent, and can be commented
 * @author 700040999
 * @author 700005222
 * @version 13/04/2021
 */
public class Comment extends Post{
    Post parent;

    /**
     * Constructor for the Comment class
     * @param o Account, the owner of this Comment
     * @param m String, the comment's message
     * @param p Post, the parent post of this Comment
     */
    public Comment(Account o, String m, Post p){
        super(o,m);
        parent = p;
    }
}
