package socialmedia;

import java.io.*;
import java.util.ArrayList;

/**
 * The SocialMedia class is an implementation of the SocialMediaPlatform interface, which represents a rudimentary
 * social media platform, with accounts and posting abilities
 * 
 * @author 700040999
 * @author 700005222
 * @version 13/04/2021
 */
public class SocialMedia implements SocialMediaPlatform {
	ArrayList<Account> accounts = new ArrayList<>();
	@Override
	public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {

		for (Account value : accounts) {
			if (value.getHandle().equals(handle)) {
				throw new IllegalHandleException("Username taken");
			}
		}

		if(handle.isEmpty()){
			throw new InvalidHandleException("Handle cannot be empty");
		} else if(handle.length() > 30){
			throw new InvalidHandleException("Handle cannot be greater than 30 characters");
		}else if (handle.contains(" ")){
			throw new InvalidHandleException("Handle cannot contain white space");
		}else {
			Account account = new Account(handle);
			//^ makes object
			accounts.add(account);
			return account.getIDNumber();
		}

	}

	@Override
	public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {
		for (Account value : accounts) {
			if (value.getHandle().equals(handle)) {
				throw new IllegalHandleException("Username taken");
			}
		}

		if(handle.isEmpty()){
			throw new InvalidHandleException("Handle cannot be empty");
		} else if(handle.length() > 30){
			throw new InvalidHandleException("Handle cannot be greater than 30 characters");
		}else if (handle.contains(" ")){
			throw new InvalidHandleException("Handle cannot contain white space");
		}else {
			Account account = new Account(handle, description);
			//^ makes object
			accounts.add(account);
			return account.getIDNumber();
		}
	}

	@Override
	public void removeAccount(int id) throws AccountIDNotRecognisedException {


		boolean flag = false;
		for(int i = 0; i < accounts.size(); i++){
			if(id ==(accounts.get(i).getIDNumber())){
				flag = true;
				for(int j = 0; j < accounts.get(i).getPosts().size();j++) {
					accounts.get(i).getPosts().get(j).deletePost();
				}
				accounts.remove(i);
				break;
			}
		}
		if(!flag){
			throw new AccountIDNotRecognisedException("The ID does not match");
		}
	}

	@Override
	public void removeAccount(String handle) throws HandleNotRecognisedException {
		boolean flag = false;
		for(int i = 0; i < accounts.size(); i++){
			if(handle.equals(accounts.get(i).getHandle())){

				flag = true;
				for(int j = 0; j < accounts.get(i).getPosts().size();j++) {
					accounts.get(i).getPosts().get(j).deletePost();
				}
				accounts.remove(i);
				break;
			}
		}
		if(!flag){
			throw new HandleNotRecognisedException("The handle does not match any account in the system");
		}


	}

	@Override
	public void changeAccountHandle(String oldHandle, String newHandle)
			throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
		boolean flag = false;
		for (Account account : accounts) {
			if (account.getHandle().equals(oldHandle)) {
				flag = true;
				break;
			}
		}

		if(!flag) {
			throw new HandleNotRecognisedException("Account does not exist in the system");
		}

		for (Account account : accounts) {
			if (account.getHandle().equals(newHandle)) {
				throw new IllegalHandleException("Username taken");
			}
		}

		if(newHandle.isEmpty()){
			throw new InvalidHandleException("Handle cannot be empty");
		} else if(newHandle.length() > 30){
			throw new InvalidHandleException("Handle cannot be greater than 30 characters");
		} else if (newHandle.contains(" ")){
			throw new InvalidHandleException("Handle cannot contain white space");
		}
		for (Account account : accounts) {
			if (account.getHandle().equals(oldHandle)) {
				account.setHandle(newHandle);
				break;
			}
		}
	}

	@Override
	public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
		for (Account account : accounts) {
			if (handle.equals(account.getHandle())) {
				account.setDescription(description);
				return;
			}
		}
		throw new HandleNotRecognisedException("No account with that handle in this database.");

	}

	@Override
	public String showAccount(String handle) throws HandleNotRecognisedException {
		for (Account account : accounts) {
			if (account.getHandle().equals(handle)) {
				return account.toString();
			}
		}
		throw new HandleNotRecognisedException("No account with that handle in the system");
	}

	@Override
	public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
		if(message.isEmpty()){
			throw new InvalidPostException("Post cannot be empty.");
		}else if (message.length()>100){
			throw new InvalidPostException("Post cannot be more than 100 characters.");
		}


		for (Account account : accounts) {
			if (account.getHandle().equals(handle)) {
				Post newPost = new Post(account, message);
				account.addPost(newPost);
				return newPost.getPostID();
			}
		}
		throw new HandleNotRecognisedException("Account not found in system.");
	}

	@Override
	public int endorsePost(String handle, int id)
			throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
		Account commentator = null;
		boolean flag = false;
		for (Account value : accounts) {
			if (value.getHandle().equals(handle)) {
				flag = true;
				commentator = value;
				break;
			}
		}
		if(!flag) {
			throw new HandleNotRecognisedException("Account not found.");
		}
		for (Account account : accounts) { // for every account
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (account.getPosts().get(j).getPostID() == id) { //if the post matches the id of the post you want to comment
					if (account.getPosts().get(j) instanceof Endorsement) { // and isn't an endorsement
						throw new NotActionablePostException("Cannot endorse an endorsement.");
					} else if (account.getPosts().get(j).isEmpty()) { //or the EmptyPost
						throw new NotActionablePostException("Empty posts cannot be endorsed");
					} else {
						Endorsement newEndorse = new Endorsement(commentator, account.getPosts().get(j));
						account.addEndorsement();//increase endorse counts
						commentator.addPost(newEndorse);//add endorsement to posts in account
						account.getPosts().get(j).addEndorsement(newEndorse); //adds endorsement to post
						return newEndorse.getPostID();
					}
				}
			}
		}
		throw new PostIDNotRecognisedException("Post ID not recognised.");


	}

	@Override
	public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
			PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
		if(message.isEmpty()){
			throw new InvalidPostException("Post cannot be empty.");
		}else if (message.length()>100){
			throw new InvalidPostException("Post cannot be more than 100 characters.");
		}
		Account commentator = null;
		boolean flag = false;
		for (Account value : accounts) {
			if (value.getHandle().equals(handle)) {
				commentator = value;
				flag = true;
				break;
			}
		}
		if(!flag) {
			throw new HandleNotRecognisedException("Account not found.");
		}
		for (Account account : accounts) { // for every account
			for (int j = 0; j < account.getPosts().size(); j++) { // and every post
				if (account.getPosts().get(j).getPostID() == id) { //if the post matches the id of the post you want to comment
					if (account.getPosts().get(j) instanceof Endorsement) { // and isn't an endorsement
						throw new NotActionablePostException("Cannot comment an endorsement.");
					}else if(account.getPosts().get(j).isEmpty()){ //or the EmptyPost
						throw new NotActionablePostException("Empty posts cannot be commented");
					} else{
						Comment newComment = new Comment(commentator, message, account.getPosts().get(j));
						commentator.addPost(newComment); //add comment to the posts array
						account.getPosts().get(j).addComment(newComment); // add comment to the post's array
						return newComment.getPostID();
					}
				}
			}
		}
		throw new PostIDNotRecognisedException("Post ID not recognised.");
	}

	@Override
	public void deletePost(int id) throws PostIDNotRecognisedException {
		for(int i = 0; i < accounts.size();i++) {
			for(int j = 0; j <accounts.get(i).getPosts().size();j++){
				if(accounts.get(i).getPosts().get(j).getPostID() == id){
					//removes post from array in account

					for (Account account : accounts) {
						for (int l = 0; l < account.getPosts().size(); l++) {
							if (account.getPosts().get(l) instanceof Endorsement) {
								//for every post in every account, if it's a endorsement with the
								//parent post being the deleted post, delete the endorsement
								if (accounts.get(i).getPosts().get(j).equals(((Endorsement) (account.getPosts().get(l))).getParent())) {
									account.getPosts().remove(l);
									account.removeEndorsement();
									l--;
								}
							}
						}
					}
					accounts.get(i).getPosts().get(j).deletePost(); // removes post
					//accounts.get(i).getPosts().remove(j);
					return;
				}
			}
		}
		throw new PostIDNotRecognisedException("Post ID not recognised");

	}

	@Override
	public String showIndividualPost(int id) throws PostIDNotRecognisedException {
		for (Account account : accounts) {
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (account.getPosts().get(j).getPostID() == id) {
					return account.getPosts().get(j).toString();
				}
			}
		}
		throw new PostIDNotRecognisedException("Post not found in system");
	}

	@Override
	public StringBuilder showPostChildrenDetails(int id)
			throws PostIDNotRecognisedException, NotActionablePostException {
		for (Account account : accounts) {
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (account.getPosts().get(j).getPostID() == id) {
					if(account.getPosts().get(j) instanceof Endorsement){
						throw new NotActionablePostException("Endorsement posts cannot have children.");
					}else {
						return account.getPosts().get(j).allComments(0);
					}
				}
			}
		}
		throw new PostIDNotRecognisedException("Post not found in system");
	}

	@Override
	public int getNumberOfAccounts() {
		return accounts.size();
	}

	@Override
	public int getTotalOriginalPosts() {
		int counter = 0;
		for (Account account : accounts) {
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (!(account.getPosts().get(j) instanceof Endorsement) && !(account.getPosts().get(j) instanceof Comment)) {
					counter++;
				}
			}
		}
		return counter;
	}

	@Override
	public int getTotalEndorsmentPosts() {
		int counter = 0;
		for (Account account : accounts) {
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (account.getPosts().get(j) instanceof Endorsement) {
					counter++;
				}
			}
		}
		return counter;
	}

	@Override
	public int getTotalCommentPosts() {
		int counter = 0;
		for (Account account : accounts) {
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (account.getPosts().get(j) instanceof Comment) {
					if(!account.getPosts().get(j).isEmpty())
						counter++;
				}
			}
		}
		return counter;
	}

	@Override
	public int getMostEndorsedPost() {
		Post mostEndorsed = accounts.get(0).getPosts().get(0);
		for (Account account : accounts) {
			for (int j = 0; j < account.getPosts().size(); j++) {
				if (mostEndorsed.getNumEndorsements() < account.getPosts().get(j).getNumEndorsements()) {
					mostEndorsed = account.getPosts().get(j);
				}
			}
		}
		return mostEndorsed.getPostID();
	}

	@Override
	public int getMostEndorsedAccount() {
		Account mostEndorse = accounts.get(0);
		for (Account account : accounts) {
			if (mostEndorse.getNumEndorsement() < account.getNumEndorsement()) {
				mostEndorse = account;
			}
		}
		return mostEndorse.getIDNumber();
	}

	@Override
	public void erasePlatform() {
		accounts.get(0).resetCounter();
		accounts.get(0).getPosts().get(0).resetCounter();
		for(int i = 0; i < accounts.size();i++){
			try {
				removeAccount(accounts.get(i).getIDNumber());

				i--;
			}catch (AccountIDNotRecognisedException ignored){

			}
		}
	}

	@Override
	public void savePlatform(String filename) throws IOException {

		FileOutputStream file = new FileOutputStream(filename);
		ObjectOutputStream out = new ObjectOutputStream(file);

		out.writeObject(accounts);

		out.close();
		file.close();


	}

	@Override
	public void loadPlatform(String filename) throws IOException, ClassNotFoundException {

		FileInputStream fis = new FileInputStream(filename);
		ObjectInputStream objectInputStream = new ObjectInputStream(fis);
		this.accounts = (ArrayList<Account>) objectInputStream.readObject();



	}

}
