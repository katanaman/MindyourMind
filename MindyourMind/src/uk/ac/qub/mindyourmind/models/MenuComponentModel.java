package uk.ac.qub.mindyourmind.models;


public class MenuComponentModel {

	private String name;
	private int image;
	private String link;
	
	public MenuComponentModel(){
		
	}
	
	public MenuComponentModel(String name, int image, String link){
		setName(name);
		setImage(image);
		setLink(link);
	}
	
	public String getName(){
		return name;
	}
	public int getImage(){
		return image;
	}
	
	public String getLink(){
		return link;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setImage(int image){
		this.image = image;
	}
	public void setLink(String link){
		this.link = link;
	}
}
