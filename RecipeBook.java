package RecipeBookProgram;
import java.io.*;
import java.util.Scanner;
import java.nio.file.*;
import static java.nio.file.StandardOpenOption.*;

/**
 * Katie Beach
 * ITP120-F01
 * 4/30/2020
 * RecipeBookProgram: RecipeBook Class
 * Contains information and functions about the recipe book.
 */

public class RecipeBook implements Serializable
{
	// Attributes of Recipe Book
	final private String NEXT = "NEXT";
	final private String QUIT = "QUIT";
	private String input = "";
	Scanner in = new Scanner(System.in);
	
	// Methods for creating, exporting, and displaying recipes
	public void createRecipe()
	{
		System.out.println("\n-------------------------------\n\n"
				+ "Please enter a Recipe Name. >> \n");
		input = in.nextLine();
		Path filePath = Paths.get("./recipe-data/" + input + ".ser");
		boolean exists = false;
		try
		{
			filePath.getFileSystem().provider().checkAccess(filePath);
			exists = true;
		}
		catch(Exception i)
		{
			System.out.println("\nYay! A new recipe!\n");
			exists = false;
		}
		if (exists == true)
		{
			boolean quit = false;
			while (quit !=true)
			{
				System.out.println("\n-------------------------------\n\n"
						+ "Oh! I believe I already know this recipe! What would you like me to do?\n\n"
						+ "1. View Recipe\n"
						+ "2. Edit Recipe\n"
						+ "3. Overwrite Recipe\n"
						+ "4. Return to Main Menu\n");
				String choice = in.nextLine();
				switch(choice)
				{
				case "1":
					System.out.println("\n-------------------------------\n");
					viewRecipe(input);
					System.out.println("\n-------------------------------\n");
					quit = false;
					break;
				case "2":
					quit = true;
					Recipe oldRecipe = deserialize(input);
					editDirectionsMenu(oldRecipe);
					break;
				case "3":
					System.out.println("\n-------------------------------\n");
					System.out.println("Yay! A new recipe!\n");
					quit = true;
					break;
				case "4":
					quit = true;
					mainMenuPrompt();
					break;
				}
			}
		}
		else
		{
			Recipe recipe = new Recipe();
			recipe.setTitle(input);
			System.out.println("\nPlease enter the Author's name. >> \n");
			recipe.setAuthor(in.nextLine());
			System.out.println("\nPlease enter the source for the recipe. (book, link, magizine, etc.) >> \n");
			recipe.setSource(in.nextLine());
			while (! input.equalsIgnoreCase(NEXT))
			{
				System.out.println("\nPlease enter an ingredient or " + NEXT + " to proceed to directions. >> \n");
				input = in.nextLine();
				if (input.equalsIgnoreCase(NEXT))
					break;
				else
					recipe.setIngredient(input);
			}
			input = "";
			while (! input.equalsIgnoreCase(NEXT))
			{
				System.out.println("\nPlease enter a step in the directions or " + NEXT + " to proceed to notes. >> \n");
				input = in.nextLine();
				if (input.equalsIgnoreCase(NEXT))
					break;
				else
					recipe.setDirection(input);
			}
			input = "";
			while (! input.equalsIgnoreCase(NEXT))
			{
				System.out.println("\nPlease enter a note or " + NEXT + " to review. >> \n");
				input = in.nextLine();
				if (input.equalsIgnoreCase(NEXT))
					break;
				else
					recipe.setNote(input);
			}
			System.out.println("\n-------------------------------\n");
			displayRecipe(recipe);
			serialize(recipe);/**/
		}
	}
	public void createRecipe(String title)
	{
		Recipe recipe = new Recipe();
		System.out.println("Is \"" + title + "\" the correct name of the recipe you would like to add? >> \n");
		String input = in.nextLine();
		if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ye") || input.equalsIgnoreCase("y"))
		{
			recipe.setTitle(in.nextLine());
		}
		else
		{
			System.out.println("\nPlease enter a Recipe Name. >> \n");
			recipe.setTitle(in.nextLine());
		}
		System.out.println("\nPlease enter the Author's name. >> \n");
		recipe.setAuthor(in.nextLine());
		System.out.println("\nPlease enter the source for the recipe. (book, link, magizine, etc.) >> \n");
		recipe.setSource(in.nextLine());
		while (! input.equalsIgnoreCase(NEXT))
		{
			System.out.println("\nPlease enter an ingredient or " + NEXT + " to proceed to directions. >> \n");
			input = in.nextLine();
			if (input.equalsIgnoreCase(NEXT))
				break;
			else
				recipe.setIngredient(input);
		}
		input = "";
		while (! input.equalsIgnoreCase(NEXT))
		{
			System.out.println("\nPlease enter a step in the directions or " + NEXT + " to proceed to notes. >> \n");
			input = in.nextLine();
			if (input.equalsIgnoreCase(NEXT))
				break;
			else
				recipe.setDirection(input);
		}
		input = "";
		while (! input.equalsIgnoreCase(NEXT))
		{
			System.out.println("\nPlease enter a note or " + NEXT + " to review. >> \n");
			input = in.nextLine();
			if (input.equalsIgnoreCase(NEXT))
				break;
			else
				recipe.setNote(input);
		}
		System.out.println("\n-------------------------------\n");
		displayRecipe(recipe);
		serialize(recipe);
	}
	public Recipe deserialize(String title)
	{
		Recipe rec = null;
		try
		{
			FileInputStream fileIn = new FileInputStream("./recipe-data/" + title + ".ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			rec = (Recipe) in.readObject();
			in.close();
			fileIn.close();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
		catch (ClassNotFoundException notFound)
		{
			System.out.println("Recipe not found.");
			System.out.println(notFound);
		}
		return rec;
	}
	public void displayRecipe(Recipe rec)
	{
		System.out.println(rec.getTitle().toUpperCase());
		System.out.println("Author: " + rec.getAuthor());
		System.out.println("Source: " + rec.getSource() + "\n");
		Object[] ingredientsList = rec.getIngredients();
		for (int x=0; x<ingredientsList.length; ++x)
			System.out.println(ingredientsList[x]);
		System.out.println("\n");
		Object[] directionsList = rec.getDirections();
		for (int x=0; x<directionsList.length; ++x)
			System.out.println((x+1) + ". " + directionsList[x]);
		System.out.println("\n");
		Object[] notesList = rec.getNotes();
		for (int x=0; x<notesList.length; ++x)
			System.out.println("** " + notesList[x]);
	}
	public void editDirectionsAdd(Recipe rec)
	{
		String edit = "";
		int step1;
		boolean quit = false;
		Object[] directionsList  = rec.getDirections();
		while (quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "After which step would you like to add directions? (Or type LIST to see the current directions or QUIT to return to the Menu) >> \n");
			edit = in.nextLine();
			if (edit.equalsIgnoreCase("LIST"))
			{
				System.out.println("\n-------------------------------\n");
				for (int x=0; x<directionsList.length; ++x)
					System.out.println((x+1) + ". " + directionsList[x]);
				quit = false;
			}
			else if (edit.equalsIgnoreCase(QUIT))
			{
				quit = true;
				break;
			}
			else
			{
				step1 = Integer.parseInt(edit);
				System.out.println("\n-------------------------------\n"
						+ step1 + ". "+ directionsList[step1-1]);
				if (step1 < directionsList.length)
						System.out.println((step1 + 1) + ". " + directionsList[step1]
								+ "\n-------------------------------\n");
				else
				{
					System.out.println("\nWhat would you like to add after step " + step1 + ". ? >> \n");
					edit = in.nextLine();
					rec.addDirection(step1, edit);
					serialize(rec);
					rec = deserialize(rec.getTitle());
					directionsList  = rec.getDirections();
					System.out.println("\n-------------------------------\n\n"
							+ "Is there another step you would like to add? (Yes/No) >> \n");
					edit = in.nextLine();
					if (edit.equalsIgnoreCase("yes") || edit.equalsIgnoreCase("y") || edit.equalsIgnoreCase("ye"))
						quit = false;
					else
					{
						System.out.println("\n-------------------------------\n");
						quit = true;
						break;
					}
				}
			}
		}
	}
	public void editDirectionsDelete(Recipe rec)
	{
		String deletion = "";
		int step;
		boolean quit = false;
		while (quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "Which step would you like to delete? (Or type LIST to see the current directions or QUIT to return to the Menu) >> \n");
			deletion = in.nextLine();
			if (deletion.equalsIgnoreCase("LIST"))
			{
				System.out.println("\n-------------------------------\n");
				Object[] directionsList  = rec.getDirections();
				for (int x=0; x<directionsList.length; ++x)
					System.out.println((x+1) + ". " + directionsList[x]);
				quit = false;
			}
			else if (deletion.equalsIgnoreCase(QUIT))
			{
				quit = true;
				break;
			}
			else
			{
				try 
				{
					step = Integer.parseInt(deletion);
					rec.deleteDirection(step);
					serialize(rec);
				} 
				catch (Exception e) 
				{
					System.out.println("\n-------------------------------\n\n"
							+ "Error. Please enter a number for the step to edit.\n" + e + "\n");
				}
				System.out.println("\n-------------------------------\n\n"
						+ "Is there another step you would like to delete? (Yes/No) >> \n");
				deletion = in.nextLine();
				if (deletion.equalsIgnoreCase("yes") || deletion.equalsIgnoreCase("y") || deletion.equalsIgnoreCase("ye"))
					quit = false;
				else
				{
					System.out.println("\n-------------------------------\n");
					quit = true;
					break;
				}
			}
		}
	}
	public void editDirectionsEdit(Recipe rec)
	{
		String edit = "";
		int step;
		Object [] directions;
		boolean quit = false;
		while (quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "Which step would you like to edit? (Or type LIST to see the current directions or QUIT to return to the Menu) >> \n");
			edit = in.nextLine();
			if (edit.equalsIgnoreCase("LIST"))
			{
				System.out.println("\n-------------------------------\n");
				Object[] directionsList  = rec.getDirections();
				for (int x=0; x<directionsList.length; ++x)
					System.out.println((x+1) + ". " + directionsList[x]);
				quit = false;
			}
			else if (edit.equalsIgnoreCase(QUIT))
			{
				quit = true;
				break;
			}
			else
			{
				try
				{
					step = Integer.parseInt(edit);
					directions = rec.getDirections();
					System.out.println("\n-------------------------------\n"
							+ directions[step-1]
							+ "\n-------------------------------\n\n"
							+ "Please enter the new directions for this step. >> \n");
					edit = in.nextLine();
					rec.editDirection(step, edit);
					serialize(rec);
				}
				catch (Exception e)
				{
					System.out.println("\n-------------------------------\n\n"
							+ "Error. Please enter a number for the step to edit.\n" + e + "\n");
				}
				System.out.println("\n-------------------------------\n\n"
						+ "Is there another step you would like to edit? (Yes/No) >> ");
				edit = in.nextLine();
				if (edit.equalsIgnoreCase("yes") || edit.equalsIgnoreCase("y") || edit.equalsIgnoreCase("ye"))
					quit = false;
				else
				{
					System.out.println("\n-------------------------------\n");
					quit = true;
					break;
				}
			}
		}
	}
	public void editDirectionsMenu(Recipe rec)
	{
		String input = "";
		boolean quit = false;
		while (quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "How would you like to edit the directions? >> \n"
					+ "1. Delete a step\n"
					+ "2. Add a step\n"
					+ "3. Edit a step\n"
					+ "4. View steps\n"
					+ "5. Return to Main Menu \n");
			input = in.nextLine();
			switch(input)
			{
			case "1":
				quit = true;
				editDirectionsDelete(rec);
				break;
			case "2":
				quit = true;
				editDirectionsAdd(rec);
				break;
			case "3":
				quit = true;
				editDirectionsEdit(rec);
				break;
			case "4":
				Object[] directions = rec.getDirections();
				System.out.println("\n-------------------------------\n");
				for (int x=0; x<directions.length; ++x)
					System.out.println((x+1) + ". " + directions[x]);
				break;
			case "5":
				quit = true;
				mainMenuPrompt();
				break;
			}
		}
	}
	public void editRecipe()
	{
		boolean quit = false;
		while(quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "Which recipe would you like to edit? (Or type LIST to view contents or QUIT to quit) >> \n");
			input = in.nextLine();
			if(input.equalsIgnoreCase("LIST"))
			{
				listRecipes();
				quit = false;
			}
			else if (input.equalsIgnoreCase(QUIT))
			{
				quit = true;
				break;
			}
			else
			{
				Recipe recipe = deserialize(input);
				Path file = Paths.get("./recipe-data/" + input + ".ser");
				try
				{
					file.getFileSystem().provider().checkAccess(file);
				}
				catch(IOException i)
				{
					System.out.println("\n-------------------------------\n");
					System.out.println("\nOh no! I found an exception! \nI'm so sorry!  I guess I don't know that recipe...\n\n"
							+ "Would you like to add \"" + input + "\" to the recipe book? >> \n");
					String recipeInput = in.nextLine();
					if (recipeInput.equalsIgnoreCase("yes") || recipeInput.equalsIgnoreCase("ye") || recipeInput.equalsIgnoreCase("y"))
					{
						quit = true;
						System.out.println("\n-------------------------------\n");
						createRecipe(input);
					}
				}
				editDirectionsMenu(recipe);
				quit = true;
			}
		}
	}
	public void exportMenu()
	{
		boolean quit = false;
		String recipe= "";
		while (quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "Which recipe would you like to export?\n (or type LIST to see current recipes or QUIT to return to menu) >> \n");
			recipe = in.nextLine();
			if (recipe.equalsIgnoreCase("list"))
			{
				System.out.println("\n-------------------------------\n");
				listRecipes();
				quit = false;
			}
			else if (recipe.equalsIgnoreCase(QUIT))
			{
				System.out.println("\n-------------------------------\n");
				quit = true;
				break;
			}
			else
			{
				try
				{
					String path = exportRecipe(recipe);
					System.out.println("\n-------------------------------\n\n"
							+ "The recipe \n" + recipe.toUpperCase() + "\nhas been exported to: \n" 
							+ path
							+ "\n\n-------------------------------\n"
							+ "\nWould you like to export another recipe? (Yes/No) >> \n");
					recipe = in.nextLine();
					if (recipe.equalsIgnoreCase("yes") || recipe.equalsIgnoreCase("ye") || recipe.equalsIgnoreCase("y"))
						quit = false;
					else
					{
						System.out.println("\n-------------------------------\n\n");
						quit = true;
						break;
					}
				}
				catch (NullPointerException npe)
				{
					System.out.println("\nOh no! I found an exception! \nI'm so sorry!  I guess I don't know that recipe...\n\n"
							+ "Would you like to add \"" + input + "\" to the recipe book? >> \n");
					String input = in.nextLine();
					if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ye") || input.equalsIgnoreCase("y"))
					{
						quit = true;
						System.out.println("\n-------------------------------\n");
						createRecipe(recipe);
					}
					else
					{
						quit = false;
					}
				}
			}
		}
	}
	public String exportRecipe(String rec)
	{
		// Declare variables.
		
// I didn't end up using this path, but I wanted to keep it here for reference in the future.
//		Path file = Paths.get(System.getProperty("user.home") + "/Documents");
		
		Recipe recipe = new Recipe();
		String export, path = "";
		Object[] exportList;
		byte[] exportData;
		OutputStream output = null;
		// Try to export the recipe.
		try
		{
			Path filePath = Paths.get("./recipe-data/" + rec + ".ser"); 
			filePath.getFileSystem().provider().checkAccess(filePath);
		}
		catch (Exception e)
		{
			System.out.println("\n-------------------------------\n\nOh no! I found an exception!");
		}
		// Get recipe.
		recipe = deserialize(rec);
		// Get and export title.
		export = recipe.getTitle().toUpperCase();
		Path file = Paths.get("exports/" + export + ".txt");
		path = file.toAbsolutePath().toString();
		try
		{
			file.getFileSystem().provider().checkAccess(file);
			Files.delete(file);
		}
		catch(IOException i)
		{
			try
			{
				Files.createFile(file);
			}
			catch(Exception e)
			{
				System.out.println(i);
				System.out.println(e);
			}
		}
		export = export + "\n";
		exportData = export.getBytes();
		try
		{
			output = new BufferedOutputStream(Files.newOutputStream(file, CREATE));
			output.write(exportData);
			output.flush();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		// Get and export author.
		export = "Author: " + recipe.getAuthor() + "\n";
		exportData = export.getBytes();
		try
		{
			output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
			output.write(exportData);
			output.flush();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		// Get and export source.
		export = "Source: " + recipe.getSource() + "\n\n";
		exportData = export.getBytes();
		try
		{
			output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
			output.write(exportData);
			output.flush();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		// Get and export ingredients.
		exportList = recipe.getIngredients();
		for (int x = 0; x < exportList.length; ++x)
		{
			export = exportList[x].toString() + "\n";
			exportData = export.getBytes();
			try
			{
				output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
				output.write(exportData);
				output.flush();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
		// Formatting (extra lines) for a different section.
		export = "\n";
		exportData = export.getBytes();
		try
		{
			output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
			output.write(exportData);
			output.flush();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		// Get and export directions.
		exportList = recipe.getDirections();
		for (int x = 0; x < exportList.length; ++x)
		{
			export = (x + 1) + ". " + exportList[x].toString() + "\n";
			exportData = export.getBytes();
			try
			{
				output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
				output.write(exportData);
				output.flush();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
		// A lot of code for a new line.  There is a better way, but I'm tired.
		export = "\n";
		exportData = export.getBytes();
		try
		{
			output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
			output.write(exportData);
			output.flush();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
		// Get and export notes.
		exportList = recipe.getNotes();
		for (int x = 0; x < exportList.length; ++x)
		{
			export = "** " + exportList[x].toString() + "\n";
			exportData = export.getBytes();
			try
			{
				output = new BufferedOutputStream(Files.newOutputStream(file, APPEND));
				output.write(exportData);
				output.flush();
				output.close();
			}
			catch (Exception e)
			{
				System.out.println(e);
			}
		}
		return file.toAbsolutePath().toString();
	}
	public void listRecipes()
	{
		File folder = new File("./recipe-data");
		File[] list = folder.listFiles();
		System.out.println("\n------ TABLE OF CONTENTS ------\n");
		for (int x=0; x <list.length; x++)
		{
			String fileName = list[x].getName();
			int extension = fileName.lastIndexOf('.');
			System.out.println(fileName.substring(0, extension));
		}
	}
	public void mainMenuPrompt()
	{
		while (input != QUIT)
		{
			System.out.println("-------------------------------\n"
					+ "\n    What would you like to do?    \n\n"
					+ "1. List Contents\n"
					+ "2. View Recipe\n"
					+ "3. Create Recipe\n"
					+ "4. Edit a Recipe's Directions\n"
					+ "5. Export Recipe\n"
					+ "6. Quit\n");
			input = in.nextLine();
			if (! input.matches("\\d+"))
				// ^^ omg, me trying to do regular expressions... 
				System.out.println("\nI'm sorry. I didn't understand that...\n\n"
						+ "Please enter the digit corresponding to your choice. >> \n");
			switch(input)
			{
			case "1":
				listRecipes();
				System.out.println();
				break;
			case "2":
				viewRecipeMenu();
				break;
			case "3":
				createRecipe();
				System.out.println("\n-------------------------------\n");
				break;
			case "4":
				editRecipe();
				break;
			case "5":
				exportMenu();
				break;
			case "6":
				input = QUIT;
				break;
			case "7":
				System.out.println("\n-------------------------------\n"
						+ "- create option to add a second part to a recipe\n"
						+ "- adjust edit function to include options to editing other parts other than directions\n"
						+ "- add option to quit before submitting an edit"
						+ "- add a delete recipe function\n"
						+ "- finally, see about adding a GUI\n"
						+ "- adjust the format for exception errors"
						+ "\n-------------------------------\n");
				input = QUIT;
			}
		}
	}
	public void serialize(Recipe rec)
	{
		try
		{
			
			String title = rec.getTitle();
			FileOutputStream fileOut = new FileOutputStream("./recipe-data/" + title + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(rec);
			out.close();
			fileOut.close();
		}
		catch (IOException e)
		{
			System.out.println(e);
		}
	}
	public void viewRecipe(String rec)
	{
		Recipe recipe = new Recipe();
		recipe = deserialize(rec);
		displayRecipe(recipe);
	}
	public void viewRecipeMenu()
	{
		boolean quit = false;
		while (quit != true)
		{
			System.out.println("\n-------------------------------\n\n"
					+ "Which recipe would you like to view?\n"
					+ " (Or type LIST to see contents or QUIT to return to the Main Menu) >> \n");
			String recipe = in.nextLine();
			if (recipe.equalsIgnoreCase("list"))
			{
				listRecipes();
				quit = false;
			}
			else if (recipe.equalsIgnoreCase(QUIT))
			{
				quit = true;
				mainMenuPrompt();
				break;
			}
			else
			{
				System.out.println("\n-------------------------------\n");
				try
				{
					viewRecipe(recipe);
					System.out.println("\n-------------------------------\n\n"
							+ "Would you like to view another recipe? (Yes/No) >> \n");
					recipe = in.nextLine();
					if (recipe.equalsIgnoreCase("yes") || recipe.equalsIgnoreCase("y") || recipe.equalsIgnoreCase("ye"))
						quit = false;
					else
					{
						System.out.println("\n-------------------------------\n");
						quit = true;
						break;
					}
				}
				catch (Exception e)
				{
					System.out.println("\nOh no! I found an exception! \nI'm so sorry!  I guess I don't know that recipe...\n\n"
							+ "Would you like to add \"" + recipe + "\" to the recipe book? >> \n");
					String input = in.nextLine();
					if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("ye") || input.equalsIgnoreCase("y"))
					{
						quit = true;
						System.out.println("\n-------------------------------\n");
						createRecipe(recipe);
					}
					else
					{
						quit = false;
					}
				}
			}
		}
	}
}