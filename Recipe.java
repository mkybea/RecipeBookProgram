package RecipeBookProgram;
/**
 * Katie Beach
 * ITP120-F01
 * 4/20/2020
 * RecipeBookProgram: Recipe Class
 * Contains information and functions about individual recipes.
 */
import java.util.ArrayList;
import java.util.*;
import java.io.*;

public class Recipe implements Serializable
{
	
	// Private Variables.
	private String title = "";
	private String author = "";
	private String source = "";
	private ArrayList<String> ingredients = new ArrayList<String>();
	private ArrayList<String> directions = new ArrayList<String>();
	private ArrayList<String> notes = new ArrayList<String>();
	
	// Constructor(s).
	// There are none needed at this moment.  The default is fine.
	
	// Get, set and other methods, in alphabetical order.
	public String getAuthor()
	{
		return author;
	}
	public void setAuthor(String authorName)
	{
		author = authorName;
	}
	public void addDirection(int step, String edit)
	{
		directions.add(step, edit);
	}
	public void deleteDirection(int step)
	{
		directions.remove(step-1);
	}
	public void editDirection(int step, String edit)
	{
		directions.remove(step-1);
		directions.add(step-1, edit);
	}
	public Object[] getDirections()
	{
		Object[] procedure = directions.toArray();
		return procedure;
	}
	public void setDirection(String step)
	{
		directions.add(step);
	}
	public Object[] getIngredients()
	{
		Object[] list = ingredients.toArray();
		return list;
	}
	public void setIngredient(String ingredient)
	{
		ingredients.add(ingredient);
	}
	public Object[] getNotes()
	{
		Object[] extras = notes.toArray();
		return extras;
	}
	public void setNote(String note)
	{
		notes.add(note);
	}
	public String getSource()
	{
		if(source == "")
		{
			String unavailable = "Original source not known.";
			return unavailable;
		}
		else
			return source;
	}
	public void setSource(String src)
	{
		source = src;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String recipeName)
	{
		title = recipeName;
	}
}
