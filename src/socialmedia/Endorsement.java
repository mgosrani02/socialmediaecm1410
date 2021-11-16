package socialmedia;

/**
 * The Endorsement class represents a Post that has no original content,
 * just retweets the original Post to a new account
 * @author 700040999
 *  @author 700005222
 *  @version 13/04/2021
 */
public class Endorsement extends Post{
    Post parent;

    /**
     * Constructor for Endorsement
     * @param o Account, owner of the Endorsement
     * @param p Post, parent post of the Endorsement
     */
    public Endorsement(Account o, Post p){
        super(o);
        parent = p;
    }

    /**
     * toString method formats the output
     * @return String, toString representation of Endorsement
     */
    public String toString(){
        return "EP@" + parent.getHandle() + ": " + parent.getMessage();
    }

    /**
     * Gets parent Post of this Endorsement
     * @return Post, the parent post
     */
    public Post getParent(){
        return parent;
    }

    /**
     *  Deletes this endorsement from the ArrayList in the owner Account
     */
    public void deleteEndorse(){
        for(int i = 0; i < super.getOwner().getPosts().size(); i++){
            if(super.getOwner().getPosts().get(i).equals(this)){
                super.getOwner().getPosts().remove(i);
                super.setOwner(null);
                break;
            }
        }
    }
}
