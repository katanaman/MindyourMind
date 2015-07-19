package uk.ac.qub.mindyourmind;

public abstract class MenuItem {

	String name;
	String image;
	String link;
	
	public MenuItem(){
		
	}
	
	public MenuItem(String name, String image, String link){
		setName(name);
		setImage(image);
		setLink(link);
	}
	
	public String getName(){
		return name;
	}
	public String getImage(){
		return image;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setImage(String image){
		this.image = image;
	}
	public void setLink(String link){
		this.link = link;
	}
}
