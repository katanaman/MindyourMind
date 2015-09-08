package uk.ac.qub.mindyourmind.activities;


public class MenuComponent {

	String name;
	String image;
	String link;
	
	public MenuComponent(){
		
	}
	
	public MenuComponent(String name, String image, String link){
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
