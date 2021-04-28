package RecipeBookProgram;
/**
 * Katie Beach
 * ITP120-F01
 * 4/30/2020
 * RecipeBookProgram
 * This program will allow a user to lookup recipe objects to view from a recipe book.
 * The recipe book object will contain functions to search, select, edit, and create new recipes.
 */
import java.io.*;
import java.nio.file.*;

public class RecipeBookProgram 
{
	
	public static void main(String[] args) 
	{
		//Create recipe (test recipe)
/*		Recipe rec = new Recipe();
		rec.setTitle("Favorite Pancakes");
		rec.setAuthor("Beaches");
		rec.setIngredient("1 1/4 cups sifted all-purpose flour");
		rec.setIngredient("3 teaspoon baking powder");
		rec.setIngredient("1 tablespoon sugar");
		rec.setIngredient("1/2 teaspoon salt");
		rec.setIngredient("1 beaten egg");
		rec.setIngredient("1 cup milk");
		rec.setIngredient("2 tablespoon salad oil");
		rec.setDirection("Sift together dry ingredients.");
		rec.setDirection("Combine egg, milk, and salad oil, then add to dry ingredients.");
		rec.setDirection("Stir until just moistened.");
		rec.setDirection("Heat a non-stick skillet on the stove and add about 1/4 cup of batter.");
		rec.setDirection("Flip pancake when bubbles begin to form on batter side and it is lightly browned on the skillet side.");
		rec.setDirection("Remove pancake when the other side is also lightly browned.");
		rec.setDirection("Repeat until all batter has been used.");
		rec.setNote("Lightly browned pancakes is a preference. Cook pancakes to your preferred taste.");/**/
		
		
		// Look for recipe data directory. If it doesn't exist make directory to hold recipe data.
		Path filePath = Paths.get("./recipe-data");
		try
		{
			filePath.getFileSystem().provider().checkAccess(filePath);
		}
		catch(IOException i)
		{
			try
			{
				boolean bool;
				File file = new File("./recipe-data");
				bool = file.mkdir();
			}
			catch(Exception e)
			{
				System.out.println(i);
				System.out.println(e);
			}
		}
		
		// Look for Export directory.  If it doesn't exist make directory to hold exported recipes.
		Path file = Paths.get("./exports");
		try
		{
			file.getFileSystem().provider().checkAccess(file);
		}
		catch(IOException i)
		{
			try
			{
				boolean bool;
				File ex = new File("./exports");
				bool = ex.mkdir();
			}
			catch(Exception e)
			{
				System.out.println(i);
				System.out.println(e);
			}
		}
		
		// Create a recipe book.
		RecipeBook recipeBook = new RecipeBook();
		
		// Display Welcome Banner.
		System.out.println("   WELCOME TO YOUR RECIPE BOOK!\n"
				+ "==================================");
		
		// Main Menu Prompt.
		recipeBook.mainMenuPrompt();
	}
}